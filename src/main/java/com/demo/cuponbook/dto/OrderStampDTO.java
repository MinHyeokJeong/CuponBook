package com.demo.cuponbook.dto;

import lombok.Data;

@Data
public class OrderStampDTO {
    private String customerPhone;
    private int paymentAmount;
    private Integer iceQty;
    private Integer hotQty;
    private Integer totalPrice;
}
