package com.tiutiu.prodiff.aspect;

import com.tiutiu.prodiff.context.UserAccountContext;
import com.tiutiu.prodiff.util.RsaUtil;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RedissonClient;

@Aspect
public class RsaDecryptAspect {
    @Resource
    RedissonClient redissonClient;
    @Pointcut("@annotation(com.tiutiu.prodiff.annotation.RsaDecrypt)")
    public void rsaDecryptPointcut() {
    }

    @Before("rsaDecryptPointcut()")
    public void rsaDecrypt(JoinPoint joinPoint) throws Exception {
        var args = joinPoint.getArgs();
        if(args == null || args.length == 0) {
            return;
        }
        Object arg = args[0];
        Long userId = UserAccountContext.getUserId();
        String cacheKey = RsaUtil.buildCacheKey(userId);
        var rsaKeyPairCache = redissonClient.getList(cacheKey);
        String privateKey = (String)rsaKeyPairCache.get(0);
        var fields = arg.getClass().getDeclaredFields();
        for(var field : fields) {
            field.setAccessible(true);
            String data = field.get(arg).toString();
            String decryptedData = RsaUtil.decryptByPrivateKey(data, privateKey);
            field.set(arg, decryptedData);
            field.setAccessible(false);
        }
    }
}
