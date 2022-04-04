package com.atguigu.gmall.SugarController;

import com.atguigu.gmall.bean3.KeywordStats;
import com.atguigu.gmall.bean3.ProductStats;
import com.atguigu.gmall.bean3.ProvinceStats;
import com.atguigu.gmall.bean3.VisitorStats;
import com.atguigu.gmall.mapper3.ProvinceStatsMapper;
import com.atguigu.gmall.service3.impl.KeywordStatsServiceImpl;
import com.atguigu.gmall.service3.impl.ProductStatsServiceImpl;
import com.atguigu.gmall.service3.impl.ProvinceStatsServiceImpl;
import com.atguigu.gmall.service3.impl.VisitorStatsServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-23 23:10
 */
@RestController
@RequestMapping("/api/test")
public class Test {
    @Autowired
    ProductStatsServiceImpl productStatsService;
    @Autowired
    ProvinceStatsServiceImpl provinceStatsService;
    @Autowired
    VisitorStatsServiceImpl visitorStatsService;
    @Autowired
    KeywordStatsServiceImpl keywordStatsService;


    @RequestMapping("/gmv")
    public String getGmv(@RequestParam(value ="date",defaultValue = "0") Integer date){
        if (date==0){
            date=now();
        }

        BigDecimal gmv = productStatsService.getGmv(date);

        StringBuilder json = new StringBuilder("{\n" +
                "    \"status\": 0,\n" +
                "    \"msg\": \"\",\n" +
                "    \"data\":"+ gmv+"\n" +
                "}\n");
        return json.toString();
    }
    /**
    {
        "status": 0,
            "data": {
        "columns": [
        { "name": "商品名称",  "id": "spu_name"
        },
        { "name": "交易额", "id": "order_amount"
        }
        ],
        "rows": [
        {
            "spu_name": "小米10",
                "order_amount": "863399.00"
        },
        {
            "spu_name": "iPhone11",
                "order_amount": "548399.00"
        }
        ]
    }
    }
**/
    @RequestMapping("/spu")
    public String  getProductStatsGroupBySpu(@RequestParam(value ="date" ,defaultValue = "0") Integer date,@RequestParam(value = "limit",defaultValue = "10") Integer limit) {
        if (date == 0) {
            date = now();
        }
        List<ProductStats> productStatsGroupBySpu = productStatsService.getProductStatsGroupBySpu(date, limit);
        StringBuilder stringBuilder = new StringBuilder(" \"status\": 0,\n" +
                "            \"data\": {\n" +
                "        \"columns\": [\n" +
                "        { \"name\": \"商品名称\",  \"id\": \"spu_name\"\n" +
                "        },\n" +
                "        { \"name\": \"交易额\", \"id\": \"order_amount\"\n" +
                "        }\n" +
                "        ],\n" +
                "        \"rows\": [");
        for (int i = 0; i < productStatsGroupBySpu.size(); i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("{\n" +
                    "            \"spu_name\": " + productStatsGroupBySpu.get(i).getSpu_name() + ",\n" +
                    "                \"order_amount\":" + productStatsGroupBySpu.get(i).getOrder_amount() + "\n" +
                    "        }");
        }
        stringBuilder.append("    ]\n" +
                "    }\n" +
                "    }");
        return stringBuilder.toString();

    }
/*
    {
     "status": 0,
     "data": {
         "categories": [
             "三星","vivo","oppo"
         ],
         "series": [
             {
                 "data": [ 406333, 709174, 681971
                 ]
             }
         ]
     }
    }
*/
@RequestMapping("/trademark")
    public String  getProductStatsByTrademark(@RequestParam(value ="date" ,defaultValue = "0") Integer date,@RequestParam(value = "limit",defaultValue = "10") Integer limit){
            if (date==0){
                date=now();
            }
        List<ProductStats> productStatsByTrademarkList = productStatsService.getProductStatsByTrademark(date, limit);
    List<String>     tradeMarkList = new ArrayList<>();
    List<BigDecimal> amountList = new ArrayList<>();
    for (ProductStats productStats:productStatsByTrademarkList){
        tradeMarkList.add(productStats.getTm_name());
        amountList.add(productStats.getOrder_amount());
    }
    String json ="    {\n" +
            "     \"status\": 0,\n" +
            "     \"data\": {\n" +
            "         \"categories\": ["
            + StringUtils.join(tradeMarkList,"\",\"")
            +"  ],\n" +
            "         \"series\": [\n" +
            "             {\n" +
            "                 \"data\": [ \n"
            +StringUtils.join(amountList,"\",\"")
            +"   ]\n" +
            "             }\n" +
            "         ]\n" +
            "     }\n" +
            "    }\n";
    return json;
};
   /*
    {
        "status": 0,
            "data": [
        {
            "name": "数码类",
                "value": 371570
        },
        {
            "name": "日用品",
                "value": 296016
        }
    ]
    }
 */
public String  getProductStatsGroupByCategory3(@RequestParam(value ="date" ,defaultValue = "0") Integer date,@RequestParam(value = "limit",defaultValue = "10") Integer limit){
    if (date==0){
        date=now();
    }
    List<ProductStats> productStatsGroupByCategory3 = productStatsService.getProductStatsGroupByCategory3(date, limit);
    StringBuilder stringBuilder = new StringBuilder(" {\n" +
            "        \"status\": 0,\n" +
            "            \"data\": [");
    for (int i=0;i<productStatsGroupByCategory3.size();i++){
        if (i > 0) {
            stringBuilder.append(",");
        }
        stringBuilder.append("{\n" +
                "            \"name\":"+ productStatsGroupByCategory3.get(i).getCategory3_name()+",\n" +
                "                \"value\":"+ productStatsGroupByCategory3.get(i).getOrder_amount()+"\n" +
                "        }");
    }
    stringBuilder.append(" ]\n" +
            "    }");
    return stringBuilder.toString();

}

    /**
     {
     "status": 0,
     "data": {
     "mapData": [
     {
     "name": "北京",
     "value": 9131
     },
     {
     "name": "天津",
     "value": 5740
     }
     ]
     }
     }

     */
    @RequestMapping("/province")
public String  getProvinceStats(@RequestParam(value = "date",defaultValue = "0") Integer date){
    if (date==0){
        date=now();
    }
        List<ProvinceStats> provinceStats = provinceStatsService.selectProvinceStats(date);
        StringBuilder stringBuilder = new StringBuilder("{\n" +
                "     \"status\": 0,\n" +
                "     \"data\": {\n" +
                "     \"mapData\": [");
        for (int i=0;i<provinceStats.size();i++){
            if (i>0){
               stringBuilder.append(",");
            }
            stringBuilder.append("{\n" +
                    "     \"name\":"+ provinceStats.get(i).getProvince_name()+",\n" +
                    "     \"value\":"+provinceStats.get(i).getOrder_amount()+"\n" +
                    "     }");
        }
        stringBuilder.append("]\n" +
                "     }\n" +
                "     }");
        return stringBuilder.toString();

    }

    /**
     {
     "status": 0,
     "data": {
     "combineNum": 1,
     "columns": [
     {
     "name": "类别",
     "id": "type"
     },
     {
     "name": "新用户",
     "id": "new"
     },
     {
     "name": "老用户",
     "id": "old"
     }
     ],
     "rows": [
     {
     "type": "用户数",
     "new": 123,
     "old": 13
     },
     {
     "type": "总访问页面",
     "new": 123,
     "old": 145
     },
     {
     "type": "跳出率",
     "new": 123,
     "old": 145
     },
     {
     "type": "平均在线时长",
     "new": 123,
     "old": 145
     },
     {
     "type": "平均访问页面数",
     "new": 23,
     "old": 145
     }
     ]
     }
     }

     */
    @RequestMapping("/visitor")
public String getVisitorStatsByNewFlag(@RequestParam(value = "date",defaultValue = "0")Integer date){
        if (date==0){
            date=now();
        }
        StringBuilder stringBuilder = new StringBuilder("  {\n" +
                "     \"status\": 0,\n" +
                "     \"data\": {\n" +
                "     \"combineNum\": 1,\n" +
                "     \"columns\": [\n" +
                "     {\n" +
                "     \"name\": \"类别\",\n" +
                "     \"id\": \"type\"\n" +
                "     },\n" +
                "     {\n" +
                "     \"name\": \"新用户\",\n" +
                "     \"id\": \"new\"\n" +
                "     },\n" +
                "     {\n" +
                "     \"name\": \"老用户\",\n" +
                "     \"id\": \"old\"\n" +
                "     }\n" +
                "     ],\n" +
                "     \"rows\": [");
        List<VisitorStats> visitorStats = visitorStatsService.gselectVisitorStatsByNewFlag(date);
        List<VisitorStats> newlist=new ArrayList<>();
        List<VisitorStats> oldlist=new ArrayList<>();
        for (int i=0;i<visitorStats.size();i++){
            if (visitorStats.get(i).getIs_new().equals("1")){
                    newlist=visitorStats;
            }else {
                oldlist=visitorStats;
            }
        }
        for (int i=0;i<visitorStats.size();i++){
            stringBuilder.append("   {\n" +
                    "     \"type\": \"用户数\",\n" +
                    "     \"new\":" +newlist.get(i).getUv_ct()+",\n" +
                    "     \"old\":"+oldlist.get(i).getUv_ct()+"\n" +
                    "     },\n" +
                    "     {\n" +
                    "     \"type\": \"总访问页面\",\n" +
                    "     \"new\":"+ newlist.get(i).getPv_ct()+",\n" +
                    "     \"old\": 145\n" +
                    "     },\n" +
                    "     {\n" +
                    "     \"type\": \"跳出率\",\n" +
                    "     \"new\": 123,\n" +
                    "     \"old\": 145\n" +
                    "     },\n" +
                    "     {\n" +
                    "     \"type\": \"平均在线时长\",\n" +
                    "     \"new\": 123,\n" +
                    "     \"old\": 145\n" +
                    "     },\n" +
                    "     {\n" +
                    "     \"type\": \"平均访问页面数\",\n" +
                    "     \"new\": 23,\n" +
                    "     \"old\": 145\n" +
                    "     }");
        }
        stringBuilder.append(" ]\n" +
                "     }\n" +
                "     }");
        return stringBuilder.toString();

    }
  /**
    {
        "status": 0,
            "data": {
        "categories": [
        "01",
                "02",
                "03",
                "04",
                "05"
        ],
        "series": [
        {
            "name": "uv",
                "data": [
            888065,
                    892945,
                    678379,
                    733572,
                    525091
                ]
        },
        {
            "name": "pv",
                "data": [
            563998,
                    571831,
                    622419,
                    675294,
                    708512
                ]
        },
        {
            "name": "新用户",
                "data": [
            563998,
                    571831,
                    622419,
                    675294,
                    708512
                ]
        }
        ]
    }
    }
*/
  @RequestMapping("/hr")
  public String  getMidStatsGroupbyHourNewFlag(@RequestParam(value = "date",defaultValue = "0")Integer date){
            if (date==0){
                date=now();
            }
      StringBuilder stringBuilder = new StringBuilder("    {\n" +
              "        \"status\": 0,\n" +
              "            \"data\": {");
      List<VisitorStats> visitorStats = visitorStatsService.selectVisitorStatsByHour(date);
      //构建24位数组
      VisitorStats[] visitorStatsArr = new VisitorStats[24];
      //把对应的小时位置赋值
      for (VisitorStats visitor:visitorStats){
          visitorStatsArr[visitor.getHr()]=visitor;
      }

      List<String> categorylist=new ArrayList<>();
      List<Long> uvList=new ArrayList<>();
      List<Long> pvList=new ArrayList<>();
      List<Long> newMemberList=new ArrayList<>();
      //循环取出固定的0-23小时  从结果map中查询对应的值
      for (int hr=0;hr<=23;hr++){
          VisitorStats visitorStats1 = visitorStatsArr[hr];
          if (visitorStats1 !=null){
              uvList.add(visitorStats1.getUv_ct());
              pvList.add(visitorStats1.getPv_ct());
              newMemberList.add(visitorStats1.getNew_uv());
          }else {
              //该小时没有流量补零
              uvList.add(0L)   ;
              pvList.add( 0L);
              newMemberList.add( 0L);
          }
          //小时数不足两位补零
          categorylist.add(String.format("%02d",hr));
      }
      //拼接字符串
      String json = "{\"status\":0,\"data\":{" + "\"categories\":" +
              "[\""+StringUtils.join(categorylist,"\",\"")+ "\"],\"series\":[" +
              "{\"name\":\"uv\",\"data\":["+ StringUtils.join(uvList,",") +"]}," +
              "{\"name\":\"pv\",\"data\":["+ StringUtils.join(pvList,",") +"]}," +
              "{\"name\":\"新用户\",\"data\":["+ StringUtils.join(newMemberList,",") +"]}]}}";
      return  json;

  }
    @RequestMapping("/keyword")
    public String getKeywordStats(@RequestParam(value = "date",defaultValue = "0") Integer date,
                                  @RequestParam(value = "limit",defaultValue = "20") int limit){
        if(date==0){
            date=now();
        }
        //查询数据
        List<KeywordStats> keywordStatsList
                = keywordStatsService.selectKeywordStats(date,limit);
        StringBuilder jsonSb=new StringBuilder( "{\"status\":0,\"msg\":\"\",\"data\":[" );
        //循环拼接字符串
        for (int i = 0; i < keywordStatsList.size(); i++) {
            KeywordStats keywordStats =  keywordStatsList.get(i);
            if(i>=1){
                jsonSb.append(",");
            }
            jsonSb.append(  "{\"name\":\"" + keywordStats.getKeyword() + "\"," +
                    "\"value\":"+keywordStats.getCt()+"}");
        }
        jsonSb.append(  "]}");
        return  jsonSb.toString();
    }

    private int now(){
        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
        return   Integer.valueOf(yyyyMMdd);
    }
}
