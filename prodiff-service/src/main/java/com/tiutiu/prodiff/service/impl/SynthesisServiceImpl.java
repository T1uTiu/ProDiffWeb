package com.tiutiu.prodiff.service.impl;

import com.tiutiu.prodiff.util.HttpClientUtil;
import com.tiutiu.prodiff.mapper.PdProjMapper;
import com.tiutiu.prodiff.entity.PdProj;
import com.tiutiu.prodiff.service.SynthesisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SynthesisServiceImpl implements SynthesisService {
    @Resource
    PdProjMapper pdProjMapper;
    @Override
    public Byte[] synthesis(String pdProjName) {
        PdProj pdProj = pdProjMapper.getPdProjByName(pdProjName);
        String filePath = pdProj.getFilePath();
        HttpClientUtil.doPost("http://localhost:8000/synthesis", null);


        return new Byte[0];
    }
}
