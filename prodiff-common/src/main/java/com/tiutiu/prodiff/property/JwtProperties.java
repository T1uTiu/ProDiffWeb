package com.tiutiu.prodiff.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "tiutiu.jwt")
@Component
@Data
public class JwtProperties {
    private String tokenName;
    private String secretkey;
    private Long ttl;
}
