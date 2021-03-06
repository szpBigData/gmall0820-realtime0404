package com.atguigu.gmall.SugarController;

import com.atguigu.gmall.bean.KeywordStats;
import com.atguigu.gmall.bean.ProductStats;
import com.atguigu.gmall.bean.ProvinceStats;
import com.atguigu.gmall.bean.VisitorStats;
import com.atguigu.gmall.service.impl.KeywordStatsServiceImpl;
import com.atguigu.gmall.service.impl.ProductStatsServiceImpl;
import com.atguigu.gmall.service.impl.ProvinceStatsServiceImpl;
import com.atguigu.gmall.service.impl.VisitorStatsServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.annotations.Param;
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
 * @create 2021-05-09 21:25
 */
@RestController
@RequestMapping("/api/sugar")
public class SugarController {
    @Autowired
    ProductStatsServiceImpl productStatsService;
    @Autowired
    ProvinceStatsServiceImpl provinceStatsService;
    @Autowired
    VisitorStatsServiceImpl visitorStatsService;
    @Autowired
    KeywordStatsServiceImpl keywordStatsService;

//    {
//        "status": 0,
//            "msg": "",
//            "data": 1201081.1632389291
//    }
//
    @RequestMapping("/gmv")
    public String getGMV(@RequestParam(value ="date" ,defaultValue = "0") Integer date){
        if (date==0){
            date=now();
        }
        BigDecimal gmv = productStatsService.getGMV(date);
        String json="{   \"status\": 0,  \"data\":" + gmv + "}";
        return json;
    }

    @RequestMapping("/spu")
    public String getProductStatsGroupBySpu(
            @RequestParam(value = "date", defaultValue = "0") Integer date,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        if (date == 0) date = now();
        List<ProductStats> statsList
                = productStatsService.getProductStatsGroupBySpu(date, limit);
        //????????????
        StringBuilder jsonBuilder =
                new StringBuilder(" " +
                        "{\"status\":0,\"data\":{\"columns\":[" +
                        "{\"name\":\"????????????\",\"id\":\"spu_name\"}," +
                        "{\"name\":\"?????????\",\"id\":\"order_amount\"}," +
                        "{\"name\":\"?????????\",\"id\":\"order_ct\"}]," +
                        "\"rows\":[");
        //??????????????????
        for (int i = 0; i < statsList.size(); i++) {
            ProductStats productStats = statsList.get(i);
            if (i >= 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("{\"spu_name\":\"" + productStats.getSpu_name() + "\"," +
                    "\"order_amount\":" + productStats.getOrder_amount() + "," +
                    "\"order_ct\":" + productStats.getOrder_ct() + "}");

        }
        jsonBuilder.append("]}}");
        return jsonBuilder.toString();
    }

    @RequestMapping("/category3")
    public String getProductStatsGroupByCategory3(@RequestParam(value ="date",defaultValue = "0") int date, @RequestParam(value = "limit",defaultValue = "4") int limit){
        if (date==0){
            date= now();
        }
        List<ProductStats> statsGroupByCategory3 = productStatsService.getProductStatsGroupByCategory3(date, limit);
        StringBuilder stringBuilder=new StringBuilder("{  \"status\": 0, \"data\": [");
        int i=0;
        for (ProductStats productStats:statsGroupByCategory3){
            if (i++>0){
                stringBuilder.append(",");
            }
            stringBuilder.append("{\"name\":\"")
                    .append(productStats.getCategory3_name()).append("\",");
            stringBuilder.append("\"value\":")
                    .append(productStats.getOrder_amount()).append("}");
            stringBuilder.append("]}");
        }
        return stringBuilder.toString();
    }

    @RequestMapping("/trademark")
    public String getProductStatsByTrademark(
            @RequestParam(value = "date", defaultValue = "0") Integer date,
            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        if (date == 0) {
            date = now();
        }
        List<ProductStats> productStatsByTrademarkList
                = productStatsService.getProductStatsByTrademark(date, limit);
        List<String> tradeMarkList = new ArrayList<>();
        List<BigDecimal> amountList = new ArrayList<>();
        for (ProductStats productStats : productStatsByTrademarkList) {
            tradeMarkList.add(productStats.getTm_name());
            amountList.add(productStats.getOrder_amount());
        }
        String json = "{\"status\":0,\"data\":{" + "\"categories\":" +
                "[\"" + StringUtils.join(tradeMarkList, "\",\"") + "\"],\"series\":[" +
                "{\"data\":[" + StringUtils.join(amountList, ",") + "]}]}}";
        return json;
    }

    @RequestMapping("/province")
    public String getProvinceStats(@RequestParam(value = "date",defaultValue="0")Integer date){
        if (date == 0) {
            date = now();
        }

        StringBuilder jsonBuilder = new StringBuilder("{\"status\":0,\"data\":{\"mapData\":[");
        List<ProvinceStats> provinceStatsList = provinceStatsService.selectProvinceStats(date);
        if (provinceStatsList.size() == 0) {
            //    jsonBuilder.append(  "{\"name\":\"??????\",\"value\":0.00}");
        }
        for (int i = 0; i < provinceStatsList.size(); i++) {
            if (i >= 1) {
                jsonBuilder.append(",");
            }
            ProvinceStats provinceStats = provinceStatsList.get(i);
            jsonBuilder.append("{\"name\":\"" + provinceStats.getProvince_name() + "\",\"value\":" + provinceStats.getOrder_amount() + " }");

        }
        jsonBuilder.append("]}}");
        return jsonBuilder.toString();
    }

    @RequestMapping("/visitor")
    public String getVisitorStatsByNewFlag(@RequestParam(value = "date", defaultValue = "0") Integer date) {
        if (date == 0) date = now();
        List<VisitorStats> visitorStatsByNewFlag = visitorStatsService.selectVisitorStatsByNewFlag(date);
        VisitorStats newVisitorStats = new VisitorStats();
        VisitorStats oldVisitorStats = new VisitorStats();
        //??????????????????????????????????????????????????????????????????
        for (VisitorStats visitorStats : visitorStatsByNewFlag) {
            if (visitorStats.getIs_new().equals("1")) {
                newVisitorStats = visitorStats;
            } else {
                oldVisitorStats = visitorStats;
            }
        }
        //???????????????????????????
        String json = "{\"status\":0,\"data\":{\"combineNum\":1,\"columns\":" +
                "[{\"name\":\"??????\",\"id\":\"type\"}," +
                "{\"name\":\"?????????\",\"id\":\"new\"}," +
                "{\"name\":\"?????????\",\"id\":\"old\"}]," +
                "\"rows\":" +
                "[{\"type\":\"?????????(???)\"," +
                "\"new\": " + newVisitorStats.getUv_ct() + "," +
                "\"old\":" + oldVisitorStats.getUv_ct() + "}," +
                "{\"type\":\"???????????????(???)\"," +
                "\"new\":" + newVisitorStats.getPv_ct() + "," +
                "\"old\":" + oldVisitorStats.getPv_ct() + "}," +
                "{\"type\":\"?????????(%)\"," +
                "\"new\":" + newVisitorStats.getUjRate() + "," +
                "\"old\":" + oldVisitorStats.getUjRate() + "}," +
                "{\"type\":\"??????????????????(???)\"," +
                "\"new\":" + newVisitorStats.getDurPerSv() + "," +
                "\"old\":" + oldVisitorStats.getDurPerSv() + "}," +
                "{\"type\":\"?????????????????????(??????)\"," +
                "\"new\":" + newVisitorStats.getPvPerSv() + "," +
                "\"old\":" + oldVisitorStats.getPvPerSv()
                + "}]}}";
        return json;
    }
    @RequestMapping("/hr")
    public String getMidStatsGroupbyHourNewFlag(@RequestParam(value = "date",defaultValue = "0") Integer date ) {
        if(date==0)  date=now();
        List<VisitorStats> visitorStatsHrList
                =visitorStatsService.selectVisitorStatsByHour(date);
        //??????24?????????
        VisitorStats[] visitorStatsArr=new VisitorStats[24];

        //??????????????????????????????
        for (VisitorStats visitorStats : visitorStatsHrList) {
            visitorStatsArr[visitorStats.getHr()] =visitorStats ;
        }
        List<String> hrList=new ArrayList<>();
        List<Long> uvList=new ArrayList<>();
        List<Long> pvList=new ArrayList<>();
        List<Long> newMidList=new ArrayList<>();

        //??????????????????0-23?????????  ?????????map?????????????????????
        for (int hr = 0; hr <=23 ; hr++) {
            VisitorStats visitorStats = visitorStatsArr[hr];
            if (visitorStats!=null){
                uvList.add(visitorStats.getUv_ct())   ;
                pvList.add( visitorStats.getPv_ct());
                newMidList.add( visitorStats.getNew_uv());
            }else{ //???????????????????????????
                uvList.add(0L)   ;
                pvList.add( 0L);
                newMidList.add( 0L);
            }
            //???????????????????????????
            hrList.add(String.format("%02d", hr));
        }
        //???????????????
        String json = "{\"status\":0,\"data\":{" + "\"categories\":" +
                "[\""+StringUtils.join(hrList,"\",\"")+ "\"],\"series\":[" +
                "{\"name\":\"uv\",\"data\":["+ StringUtils.join(uvList,",") +"]}," +
                "{\"name\":\"pv\",\"data\":["+ StringUtils.join(pvList,",") +"]}," +
                "{\"name\":\"?????????\",\"data\":["+ StringUtils.join(newMidList,",") +"]}]}}";
        return  json;
    }
    @RequestMapping("/keyword")
    public String getKeywordStats(@RequestParam(value = "date",defaultValue = "0") Integer date,
                                  @RequestParam(value = "limit",defaultValue = "20") int limit){
        if(date==0){
            date=now();
        }
        //????????????
        List<KeywordStats> keywordStatsList
                =keywordStatsService.selectKeywordStats(date,limit);
        StringBuilder jsonSb=new StringBuilder( "{\"status\":0,\"msg\":\"\",\"data\":[" );
        //?????????????????????
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
