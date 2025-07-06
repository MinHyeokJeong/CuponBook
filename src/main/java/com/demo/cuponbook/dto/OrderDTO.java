package com.demo.cuponbook.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Integer iceQty;
    private Integer hotQty;
    private Integer totalPrice;
}
