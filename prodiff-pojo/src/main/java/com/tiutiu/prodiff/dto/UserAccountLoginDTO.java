package com.tiutiu.prodiff.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAccountLoginDTO implements Serializable {
    private String email;
    private String password;
}
