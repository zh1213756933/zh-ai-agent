package com.zhou.zhaiagent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Tag(name = "健康检查", description = "健康检查")
public class HealthController {

    @Operation(summary = "测试检查接口")
    @GetMapping("/check")
    public String checkHealth() {
        return "Love You, Ameath";
    }

}
