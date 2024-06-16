package com.tiutiu.prodiff.controller;

import com.tiutiu.prodiff.constant.UserAccountConstant;
import com.tiutiu.prodiff.entity.UserAccount;
import com.tiutiu.prodiff.property.JwtProperties;
import com.tiutiu.prodiff.result.Result;
import com.tiutiu.prodiff.service.CommonService;
import com.tiutiu.prodiff.service.UserAccountService;
import com.tiutiu.prodiff.util.RsaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/common")
@Tag(name = "通用接口")
public class CommonController {
    @Resource
    CommonService commonService;
    @Resource
    UserAccountService userAccountService;
    @Resource
    RedissonClient redissonClient;
    @Resource
    JwtProperties jwtProperties;
    @GetMapping("/rsaPublicKey")
    @Operation(summary = "获取RSA公钥")
    public Result<String> getRsaPublicKey(@RequestParam(required = false) String email){
        Long userId;
        if(email == null){
            userId = UserAccountConstant.GUEST_USER_ID;
        }else{
            UserAccount userAccount = userAccountService.getUserAccount(email);
            if(userAccount == null){
                return Result.fail(UserAccountConstant.ACCOUNT_NULL);
            }
            userId = userAccount.getId();
        }

        String cacheKey = RsaUtil.buildCacheKey(userId);
        RList<String> rsaKeyPairCache = redissonClient.getList(cacheKey);
        List<String> keyPair;
        if(rsaKeyPairCache.isExists()){
            keyPair = rsaKeyPairCache.readAll();
        }else{
            keyPair = commonService.buildRsaKeyPair(userId);
            if(keyPair == null){
                return Result.fail("RSA密钥生成失败");
            }
            rsaKeyPairCache.clear();
            rsaKeyPairCache.add(keyPair.get(0));
            rsaKeyPairCache.add(keyPair.get(1));
            rsaKeyPairCache.expire(Duration.ofMillis(jwtProperties.getTtl()));
        }

        return Result.success(keyPair.get(1));
    }
}
