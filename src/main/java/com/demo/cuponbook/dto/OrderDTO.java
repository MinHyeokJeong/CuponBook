package com.demo.cuponbook.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private int iceQty;
    private int hotQty;
    private int totalPrice;
}
