package com.haizhou.smarttax.controller;

import com.haizhou.smarttax.common.Result;
import com.haizhou.smarttax.entity.ApiKey;
import com.haizhou.smarttax.service.ApiKeyService;
import com.haizhou.smarttax.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/apikey")
public class ApiKeyController {
    
    @Autowired
    private ApiKeyService apiKeyService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/generate")
    public Result<ApiKey> generateApiKey(
            @RequestHeader("Authorization") String authorization) {
        
        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(token);
        
        ApiKey apiKey = apiKeyService.generateApiKey(userId);
        return Result.success(apiKey);
    }
    
    @GetMapping("/list")
    public Result<List<ApiKey>> getUserApiKeys(
            @RequestHeader("Authorization") String authorization) {
        
        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(token);
        
        List<ApiKey> apiKeys = apiKeyService.getUserApiKeys(userId);
        return Result.success(apiKeys);
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> revokeApiKey(@PathVariable Long id) {
        apiKeyService.revokeApiKey(id);
        return Result.success();
    }
    
    @PostMapping("/validate")
    public Result<Boolean> validateApiKey(@RequestParam String apiKey) {
        boolean valid = apiKeyService.validateApiKey(apiKey);
        return Result.success(valid);
    }
}
