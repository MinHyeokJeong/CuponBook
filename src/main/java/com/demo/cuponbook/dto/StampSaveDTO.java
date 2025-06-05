package com.demo.cuponbook.dto;

import lombok.Data;

@Data
public class StampSaveDTO {
    private String customerPhone;
    private int paymentAmount;
}
