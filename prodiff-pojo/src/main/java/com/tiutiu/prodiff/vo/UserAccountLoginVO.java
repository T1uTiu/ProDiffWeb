package com.tiutiu.prodiff.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserAccountLoginVO implements Serializable {
    private Long id;
    private String email;
    private String username;
    private String token;
}
