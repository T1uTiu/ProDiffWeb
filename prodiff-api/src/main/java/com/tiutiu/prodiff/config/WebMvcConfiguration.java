package com.tiutiu.prodiff.config;

import com.tiutiu.prodiff.interceptor.JwtAuthenticationInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Resource
    JwtAuthenticationInterceptor jwtAuthenticationInterceptor;
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/verificationCode")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/common/rsaPublicKey");
    }
    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .title("ProDiff Web Api文档")
                .description("ProDiff Web Api文档")
                .version("1.0");
        return new OpenAPI()
                .info(info);
    }
}
