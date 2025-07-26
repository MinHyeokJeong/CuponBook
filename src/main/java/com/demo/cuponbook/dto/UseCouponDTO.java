package com.demo.cuponbook.dto;

import lombok.Data;

@Data
public class UseCouponDTO {
    private int usedCoupon;
    private int useableCoupon;
    private String phone;

    private Integer orderIceQty;
    private Integer orderHotQty;
    private Integer orderTotalPrice;
}
