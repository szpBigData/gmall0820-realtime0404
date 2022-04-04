package com.atguigu.gmall.bean3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author sunzhipeng
 * @create 2021-10-24 19:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceStats {
    private String stt;
    private String edt;
    private String province_id;
    private String province_name;
    private BigDecimal order_amount;
    private String ts;

}
