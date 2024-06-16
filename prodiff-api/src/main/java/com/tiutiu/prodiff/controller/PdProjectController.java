package com.tiutiu.prodiff.controller;

import com.tiutiu.prodiff.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pDProject")
@Tag(name = "工程文件")
public class PdProjectController {
    @PostMapping("/insert")
    @Operation(summary = "新增")
    public Result<?> insertProject(String projectName) {
        return null;
    }
}
