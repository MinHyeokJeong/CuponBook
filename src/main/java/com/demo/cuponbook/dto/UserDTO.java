package com.demo.cuponbook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.demo.cuponbook.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    private String userID;
    private String userPwd;
    private String userName;
    private String userEmail;
    private String userPhone;

    public static UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUserID(userEntity.getUserID());
        userDTO.setUserPwd(userEntity.getUserPwd());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setUserEmail(userEntity.getUserEmail());
        userDTO.setUserPhone(userEntity.getUserPhone());
        return userDTO;
    }

}
