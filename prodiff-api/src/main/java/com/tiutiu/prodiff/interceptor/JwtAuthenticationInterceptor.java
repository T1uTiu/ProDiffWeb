package com.tiutiu.prodiff.interceptor;

import com.tiutiu.prodiff.constant.UserAccountConstant;
import com.tiutiu.prodiff.context.UserAccountContext;
import com.tiutiu.prodiff.property.JwtProperties;
import com.tiutiu.prodiff.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    @Resource
    JwtProperties jwtProperties;
    @Resource
    RedissonClient redissonClient;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String token = request.getHeader(jwtProperties.getTokenName());
        RBucket<String> rBucket = redissonClient.getBucket(UserAccountConstant.INVALID_TOKEN + token);
        if(rBucket.isExists()){
            response.setStatus(401);
            return false;
        }

        Claims claims = JwtUtil.parseJwt(jwtProperties.getSecretkey(), token);
        if(claims == null){
            response.setStatus(401);
            return false;
        }
        Long userId = Long.valueOf(claims.get("userId").toString());
        UserAccountContext.setUserId(userId);
        UserAccountContext.setToken(token);
        return true;
    }
}
