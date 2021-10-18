package com.atguigu.gmall.realtime.bean;

/**
 * @author sunzhipeng
 * @create 2021-04-17 21:50
 */

import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: Felix
 * Desc: 支付信息实体类
 */
@Data
public class PaymentInfo {
    Long id;
    Long order_id;
    Long user_id;
    BigDecimal total_amount;
    String subject;
    String payment_type;
    String create_time;
    String callback_time;
}
