package com.demo.cuponbook.dto;

import lombok.Data;

@Data
public class CouponUseRequest {
    private String phone;
    private int usedCouponCnt;
    private int couponCnt;
}
