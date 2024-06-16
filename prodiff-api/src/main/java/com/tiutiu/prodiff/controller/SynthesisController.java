package com.tiutiu.prodiff.controller;

import com.tiutiu.prodiff.result.Result;
import com.tiutiu.prodiff.service.SynthesisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/synthesis")
@Tag(name = "歌声合成")
public class SynthesisController {
    @Autowired
    SynthesisService synthesisService;


    @GetMapping()
    @Operation(summary = "合成")
    public Result<Byte[]> synthesis(String pdProjName) {
        return Result.success(synthesisService.synthesis(pdProjName));
    }
}
