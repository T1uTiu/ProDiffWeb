package com.tiutiu.prodiff.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAccountRegisterDTO implements Serializable {
    private String username;
    private String email;
    private String verificationCode;
    private String password;
}
