package com.demo.cuponbook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import com.demo.cuponbook.dto.UserDTO;

@Entity
@Data
@Table(name="user")
public class UserEntity {
    @Id
    private String userID;

    @Column(length = 100)
    private String userPwd;

    @Column(length = 100)
    private String userName;

    @Column(length = 100)
    private String userEmail;

    @Column(length = 100)
    private String userPhone;

    @Builder
    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.userID = userDTO.getUserID();
        userEntity.userPwd = userDTO.getUserPwd();
        userEntity.userName = userDTO.getUserName();
        userEntity.userEmail = userDTO.getUserEmail();
        userEntity.userPhone = userDTO.getUserPhone();
        return userEntity;
    }
}

