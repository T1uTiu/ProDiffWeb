package com.tiutiu.prodiff.service.impl;

import com.tiutiu.prodiff.property.JwtProperties;
import com.tiutiu.prodiff.service.CommonService;
import com.tiutiu.prodiff.util.RsaUtil;
import jakarta.annotation.Resource;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public List<String> buildRsaKeyPair(Long userId) {
        List<String> keyPair = RsaUtil.buildRsaKeyPair();
        if(keyPair == null || keyPair.size() != 2) {
            return null;
        }
        return keyPair;
    }
}
