package com.atguigu.gmall.SugarController;


import com.atguigu.gmall.bean2.KeywordStats;
import com.atguigu.gmall.bean2.ProductStats;
import com.atguigu.gmall.bean2.ProvinceStats;

import com.atguigu.gmall.service2.impl.KeywordStatsServiceImpl;
import com.atguigu.gmall.service2.impl.ProductStatsServiceImpl;
import com.atguigu.gmall.service2.impl.ProvinceStatsServiceImpl;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-10 15:11
 */
@RestController
@RequestMapping("/api/sugar2")
public class TestController {
    @Autowired
    ProductStatsServiceImpl productStatsService;
    @Autowired
    KeywordStatsServiceImpl keywordStatsService;
    @Autowired
    ProvinceStatsServiceImpl provinceStatsService;
/**
 * 	访问路径
 * /api/sugar/gmv
 * 	返回格式
 * {
 *     "status": 0,
 *     "msg": "",
 *     "data": 1201081.1632389291
 * }
 */
@RequestMapping("/gmv")
public String getGMV(@RequestParam(value = "date",defaultValue ="0" )Integer date){
    StringBuilder builder = new StringBuilder("{\n" +
            "    \"status\": 0,\n" +
            "    \"msg\": \"\",\n" +
            "    \"data\": ");
//    BigDecimal gmv = productStatsService.getGMV(date);
//    builder.append(gmv);
    builder.append(20);
    builder.append("}\n");

    return  builder.toString();
}
//商品交易额
/*
{
    "status": 0,
        "msg": "",
        "data": {
    "categories": [
    "苹果",
            "三星",
            "华为",
            "oppo",
            "vivo",
            "小米29"
        ],
    "series": [
    {
        "name": "手机品牌",
            "data": [
        7562,
                5215,
                6911,
                8565,
                6800,
                7691
                ]
    }
        ]
}
}
*/





    //品牌分布
    /*
    {
    "status": 0,
    "msg": "",
    "data": [
        {
            "name": "windows phone",
            "value": 29
        },
        {
            "name": "Nokia S60",
            "value": 2
        },
        {
            "name": "Nokia S90",
            "value": 1
        }
    ]
}
     */
    public String  getProductStatsGroupByCategory(@RequestParam(value = "date",defaultValue ="0" )Integer date,
                                                  @RequestParam(value = "limit",defaultValue ="0" )Integer limit
    ){
        StringBuilder stringBuilder = new StringBuilder("{\n" +
                "    \"status\": 0,\n" +
                "    \"msg\": \"\",\n" +
                "    \"data\": [");
        List<ProductStats> productStatsGroupByCategory = productStatsService.getProductStatsGroupByCategory(date, limit);


        for (int i=0;i<productStatsGroupByCategory.size();i++){
            if (i>0){
                stringBuilder.append(",");
            }
            ProductStats productStats = productStatsGroupByCategory.get(i);

            stringBuilder.append(" {\"name\":\"" + productStats.getCategory3_name() + "\"," +
                    .append(productStats.getOrder_amount()).append("}");
            stringBuilder.append("]}");
        }
        return stringBuilder.toString();
    }
//商品排行

    /**
     {
     "status": 0,
     "msg": "",
     "data": {
     "columns": [
     {
     "name": "商品名称",
     "id": "spu_name"
     },
     {
     "name": "成交金额",
     "id": "amount"
     }
     ],
     "rows": [
     {
     "spu_name": "商品1",
     "amount": "金额1"
     },
     {
     "spu_name": "商品2",
     "amount": "金额2"
     },
     {
     "spu_name": "商品3",
     "amount": "金额3"
     }
     ]
     }
     }

     */
    @RequestMapping("/spu")
public String   getProductStatsGroupBySpu(@RequestParam(value = "date",defaultValue ="0" )Integer date,
                                          @RequestParam(value = "limit",defaultValue ="0" )Integer limit
                                          ){
        StringBuilder stringBuilder = new StringBuilder("{\n" +
                "     \"status\": 0,\n" +
                "     \"msg\": \"\",\n" +
                "     \"data\": {\n" +
                "     \"columns\": [\n" +
                "     {\n" +
                "     \"name\": \"商品名称\",\n" +
                "     \"id\": \"spu_name\"\n" +
                "     },\n" +
                "     {\n" +
                "     \"name\": \"成交金额\",\n" +
                "     \"id\": \"amount\"\n" +
                "     }\n" +
                "     ],\n" +
                "     \"rows\": [");
        List<ProductStats> productStatsGroupBySpu = productStatsService.getProductStatsGroupBySpu(date, limit);
        for (int i=0;i<productStatsGroupBySpu.size();i++){
            ProductStats productStats = productStatsGroupBySpu.get(i);
            if (i>0){
                stringBuilder.append(",");
            }
            stringBuilder.append("{\"spu_name\":\"" + productStats.getSpu_name() + "\"," +
                    "\"order_amount\":" + productStats.getOrder_amount() + "," +
                    "\"order_ct\":" + productStats.getOrder_ct() + "}");

        }
        stringBuilder.append("]\n" +
                "     }\n" +
                "     }");

        return stringBuilder.toString();
    }
//第5章  分省市热力图统计
@Autowired
ProvinceStatsServiceImpl provinceStatsService;
@RequestMapping("/province")
    public String getProvinceStats(@RequestParam(value="date",defaultValue = "0") Integer date){
    if (date==0){
        date=now();
    }
    StringBuilder stringBuilder = new StringBuilder("{\n" +
            "    \"status\": 0,\n" +
            "    \"data\": {\n" +
            "        \"mapData\": [\n");
    List<ProvinceStats> provinceStats = provinceStatsService.selectProvinceStats(date);
    for (int i=0;i<provinceStats.size();i++){
        if (i>0){
            stringBuilder.append(",");
        }
        ProvinceStats provinceStats1 = provinceStats.get(i);
        stringBuilder.append("{\"name\":\"" + provinceStats1.getProvince_name() + "\",\"value\":" + provinceStats1.getOrder_amount()+ " }");
    }
    stringBuilder.append("]}}");
    return stringBuilder.toString();
}





//第七章   热词字符云
    /**
   	访问路径
    $API_HOST/api/sugar/keyword
	返回值格式
    {
        "status": 0,
            "data": [
        {
            "name": "data",
                "value": 60679,
        },
        {
            "name": "dataZoom",
                "value": 24347,
        }
    ]
    }
*/


//第7章：热词字符云
//	访问路径
//    $API_HOST/api/sugar/keyword
//	返回值格式
//    {
//        "status": 0,
//            "data": [
//        {
//            "name": "data",
//                "value": 60679,
//        },
//        {
//            "name": "dataZoom",
//                "value": 24347,
//        }
//    ]
//    }
@RequestMapping("/keyword")
public String getKeyeordStats(@RequestParam(value = "date",defaultValue = "0") Integer date,
                              @RequestParam(value = "limit",defaultValue = "20") int limit
){
    if (date==0){
        date=now();
    }


    StringBuilder stringBuilder = new StringBuilder("{\n" +
            "    \"status\": 0,\n" +
            "    \"data\": [\n");
    List<KeywordStats> keywordStats = keywordStatsService.selectKeywordStats(date, limit);
    for (int i=0;i<keywordStats.size();i++){
        KeywordStats keywordStats1 = keywordStats.get(i);
        if (i>0){
            stringBuilder.append(",");
        }
       stringBuilder.append(  "{\"name\":\"" + keywordStats1.getKeyword()+ "\"," +
                "\"value\":"+keywordStats1.getCt()+"}");
    }
    stringBuilder.append("]}");
    return stringBuilder.toString();

}

    private int now(){
        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
        return   Integer.valueOf(yyyyMMdd);
    }
}
