package com.haizhou.smarttax.controller;

import com.haizhou.smarttax.common.Result;
import com.haizhou.smarttax.dto.LoginRequest;
import com.haizhou.smarttax.dto.RegisterRequest;
import com.haizhou.smarttax.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public Result<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        Map<String, Object> result = userService.register(request);
        return Result.success(result);
    }
    
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        Map<String, Object> result = userService.login(request);
        return Result.success(result);
    }
    
    @GetMapping("/token")
    public Result<Map<String, String>> getToken() {
        // 简单的token获取接口，实际应用中应该验证API Key
        Map<String, String> result = Map.of("message", "请使用登录接口获取token");
        return Result.success(result);
    }
}
