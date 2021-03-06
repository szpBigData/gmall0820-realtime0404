package com.atguigu.gmall.realtime.app.dwd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.realtime.utils.MyKafkaUtil;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sunzhipeng
 * @create 2021-03-21 22:17
 * 从kafka读取ODS层用户行为数据
 */
public class BaseLogApp {
    //定义用户行为主题信息
    private static final String TOPIC_START = "dwd_start_log";
    private static final String TOPIC_PAGE = "dwd_page_log";
    private static final String TOPIC_DISPLAY = "dwd_display_log";

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置并行度与kafka分区一致
        env.setParallelism(4);
        //设置精准一次性保证（默认）  每5000ms开始一次checkpoint
        env.enableCheckpointing(5000, CheckpointingMode.EXACTLY_ONCE);
        //Checkpoint必须在一分钟内完成，否则就会被抛弃
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        env.setStateBackend(new FsStateBackend("hdfs://hadoop101:8020/gmall/flink/checkpoint"));
        System.setProperty("HADOOP_USER_NAME", "test");

        //指定消费者配置信息
        String groupId = "ods_dwd_base_log_app";
        String topic = "ods_base_log";
        //TODO 1.从kafka中读取数据
        //调用Kafka工具类，从指定Kafka主题读取数据
        FlinkKafkaConsumer<String> kafkaSource = MyKafkaUtil.getKafkaSource(topic, groupId);
        //添加到数据源
        DataStreamSource<String> kafkaDS = env.addSource(kafkaSource);
        //转换为json对象
        SingleOutputStreamOperator<JSONObject> jsonObjectDS = kafkaDS.map(
                new MapFunction<String, JSONObject>() {
                    public JSONObject map(String value) throws Exception {
                        JSONObject jsonObject = JSON.parseObject(value);
                        return jsonObject;
                    }
                }
        );
        //TODO 2.识别新老访客
        //保存每个mid的首次访问日期，每条进入该算子的访问记录，都会把mid对应的首次访问时间读取出来，跟当前日期进行比较，只有首次访问时间不为空，且首次访问时间早于当日的，则认为该访客是老访客，否则是新访客。
        //同时如果是新访客且没有访问记录的话，会写入首次访问时间。
        //按照mid进行分组
        KeyedStream<JSONObject, String> midKeyedDS =
                jsonObjectDS.keyBy(data -> data.getJSONObject("common").getString("mid"));
        //校验采集到的数据是新老访客
        SingleOutputStreamOperator<JSONObject> midWithNewFlagDS = midKeyedDS.map(
                new RichMapFunction<JSONObject, JSONObject>() {
                    //声明第一次访问日期的状态
                    private ValueState<String> firstVisitDataState;
                    //声明日期数据格式化对象
                    private SimpleDateFormat simpleDateFormat;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        //初始化数据
                        firstVisitDataState = getRuntimeContext().getState(
                                new ValueStateDescriptor<String>("newMidDateState", String.class)
                        );
                        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                    }

                    @Override
                    public JSONObject map(JSONObject jsonObj) throws Exception {
                        //打印数据
                        System.out.println(jsonObj);
                        //获取访问标记   0表示老访客  1表示新访客
                        String isNew = jsonObj.getJSONObject("common").getString("is_new");
                        //获取数据中的时间戳
                        Long ts = jsonObj.getLong("ts");

                        //判断标记如果为"1",则继续校验数据
                        if ("1".equals(isNew)) {
                            //获取新访客状态
                            String newMidDate = firstVisitDataState.value();
                            //获取当前数据访问日期
                            String tsDate = simpleDateFormat.format(new Date(ts));

                            //如果新访客状态不为空,说明该设备已访问过 则将访问标记置为"0"
                            if (newMidDate != null && newMidDate.length() != 0) {
                                if (!newMidDate.equals(tsDate)) {
                                    isNew = "0";
                                    jsonObj.getJSONObject("common").put("is_new", isNew);
                                }
                            } else {
                                //如果复检后，该设备的确没有访问过，那么更新状态为当前日期
                                firstVisitDataState.update(tsDate);
                            }
                        }
                        //返回确认过新老访客的json数据
                        return jsonObj;
                    }
                }
        );
//TODO 3.利用侧输出流实现数据拆分
//定义启动和曝光数据的侧输出流标签
        OutputTag<String> startTag = new OutputTag<String>("start") {
        };
        OutputTag<String> displayTag = new OutputTag<String>("display") {
        };

//日志页面日志、启动日志、曝光日志
//将不同的日志输出到不同的流中 页面日志输出到主流,启动日志输出到启动侧输出流,曝光日志输出到曝光日志侧输出流
        SingleOutputStreamOperator<String> pageDStream = midWithNewFlagDS.process(
                new ProcessFunction<JSONObject, String>() {
                    @Override
                    public void processElement(JSONObject jsonObj, Context ctx, Collector<String> out) throws Exception {
                        //获取数据中的启动相关字段
                        JSONObject startJsonObj = jsonObj.getJSONObject("start");
                        //将数据转换为字符串，准备向流中输出
                        String dataStr = jsonObj.toString();
                        //如果是启动日志，输出到启动侧输出流
                        if (startJsonObj != null && startJsonObj.size() > 0) {
                            ctx.output(startTag, dataStr);
                        } else {
                            //非启动日志,则为页面日志或者曝光日志(携带页面信息)
                            System.out.println("PageString:" + dataStr);
                            //将页面数据输出到主流
                            out.collect(dataStr);
                            //获取数据中的曝光数据,如果不为空,则将每条曝光数据取出输出到曝光日志侧输出流
                            JSONArray displays = jsonObj.getJSONArray("displays");
                            if (displays != null && displays.size() > 0) {
                                for (int i = 0; i < displays.size(); i++) {
                                    JSONObject displayJsonObj = displays.getJSONObject(i);
                                    //获取页面id
                                    String pageId = jsonObj.getJSONObject("page").getString("page_id");
                                    //给每条曝光信息添加上pageId
                                    displayJsonObj.put("page_id", pageId);
                                    //将曝光数据输出到测输出流
                                    ctx.output(displayTag, displayJsonObj.toString());
                                }
                            }
                        }
                    }
                }
        );

//获取侧输出流
        DataStream<String> startDStream = pageDStream.getSideOutput(startTag);
        DataStream<String> displayDStream = pageDStream.getSideOutput(displayTag);
//TODO 4.将数据输出到kafka不同的主题中
        FlinkKafkaProducer<String> startSink = MyKafkaUtil.getKafkaSink(TOPIC_START);
        FlinkKafkaProducer<String> pageSink = MyKafkaUtil.getKafkaSink(TOPIC_PAGE);
        FlinkKafkaProducer<String> displaySink = MyKafkaUtil.getKafkaSink(TOPIC_DISPLAY);

        startDStream.addSink(startSink);
        pageDStream.addSink(pageSink);
        displayDStream.addSink(displaySink);
        //todo 5 分流  根据日志数据内容，将日志分为3类，页面日志，启动日志和曝光日志
        //页面日志输出到主流，启动日志输出到启动侧输出流，曝光日志输出到曝光日志侧输出流
        //侧输出流：1.接收迟到数据 2.分流
        //定义侧输出流


        //执行
        env.execute("dwd_base_log Job");
    }
}
