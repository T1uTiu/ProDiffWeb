package com.tiutiu.prodiff.service.impl;

import com.tiutiu.prodiff.constant.UserAccountConstant;
import com.tiutiu.prodiff.context.UserAccountContext;
import com.tiutiu.prodiff.property.JwtProperties;
import com.tiutiu.prodiff.util.JwtUtil;
import com.tiutiu.prodiff.mapper.UserAccountMapper;
import com.tiutiu.prodiff.dto.UserAccountLoginDTO;
import com.tiutiu.prodiff.dto.UserAccountRegisterDTO;
import com.tiutiu.prodiff.entity.UserAccount;
import com.tiutiu.prodiff.service.UserAccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {
    @Resource
    UserAccountMapper userAccountMapper;
    @Resource
    JwtProperties jwtProperties;
    @Resource
    RedissonClient redissonClient;
    @Resource
    AmqpTemplate amqpTemplate;

    @Override
    public UserAccount login(UserAccountLoginDTO userAccountLoginDTO) {
        String email = userAccountLoginDTO.getEmail();
        UserAccount userAccount = getUserAccount(email);
        if(userAccount == null){
            return null;
        }
        String password = userAccountLoginDTO.getPassword();
        String userPassword = userAccount.getPassword();
        if(!password.equals(userPassword)){
            return null;
        }
        return userAccount;
    }

    @Override
    public String buildToken(UserAccount userAccount) {
        Map<String, Object> claims = new HashMap<>();
        Long userId = userAccount.getId();
        claims.put("userId", userId);
        claims.put("createTime", System.currentTimeMillis());
        return JwtUtil.createJwt(
                jwtProperties.getSecretkey(),
                jwtProperties.getTtl(),
                claims
        );
    }

    @Override
    public void sendEmailVerificationCode(String email, String address) {
        synchronized (address.intern()) {
            Random random = new Random();
            int code = random.nextInt(899999) + 100000;
            RBucket<String> rBucket = redissonClient.getBucket(UserAccountConstant.VERIFY_EMAIL_DATA+email);
            rBucket.set(String.valueOf(code), Duration.ofMinutes(5));
            Map<String, Object> data = new HashMap<>();
            data.put("email", email);
            data.put("code", code);
            amqpTemplate.convertAndSend(UserAccountConstant.MQ_MAIL, data);
        }

    }

    @Override
    public String register(UserAccountRegisterDTO userAccountRegisterDTO) {
        String email = userAccountRegisterDTO.getEmail();
        UserAccount userAccount = userAccountMapper.getUserAccountByEmail(email);
        if(userAccount != null){
            return UserAccountConstant.REGISTER_EMAIL_EXIST;
        }
        String code = this.getVerificationCode(email);
        if(code == null){
            return UserAccountConstant.REGISTER_CODE_NULL;
        }
        if(!code.equals(userAccountRegisterDTO.getVerificationCode())){
            return UserAccountConstant.REGISTER_CODE_ERROR;
        }
        userAccount = new UserAccount();
        userAccount.setEmail(email);
        userAccount.setPassword(userAccountRegisterDTO.getPassword());
        userAccount.setUsername(userAccountRegisterDTO.getUsername());
        userAccountMapper.insertUserAccount(userAccount);
        return null;
    }

    @Override
    public void logout() {
        String token = UserAccountContext.getToken();
        Long ttl = jwtProperties.getTtl();
        RBucket<String> rBucket = redissonClient.getBucket(UserAccountConstant.INVALID_TOKEN+token);
        rBucket.set("1", Duration.ofMillis(ttl));
    }

    private String getVerificationCode(String email){
        RBucket<String> rBucket = redissonClient.getBucket(UserAccountConstant.VERIFY_EMAIL_DATA+email);
        return rBucket.get();
    }
    public UserAccount getUserAccount(String email){
        return userAccountMapper.getUserAccountByEmail(email);
    }
}
