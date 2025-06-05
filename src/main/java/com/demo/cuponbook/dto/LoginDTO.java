package com.demo.cuponbook.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class LoginDTO {
    private String userID;
    private String userPwd;
}
