package com.tiutiu.prodiff.entity;

import lombok.Data;

@Data
public class UserAccount {
    Long id;
    String username;
    String email;
    String password;
}
