package com.haizhou.smarttax.service;

import com.haizhou.smarttax.entity.ApiKey;
import com.haizhou.smarttax.mapper.ApiKeyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ApiKeyService {
    
    @Autowired
    private ApiKeyMapper apiKeyMapper;
    
    public ApiKey generateApiKey(Long userId) {
        ApiKey apiKey = new ApiKey();
        apiKey.setUserId(userId);
        apiKey.setApiKey("sk_" + UUID.randomUUID().toString().replace("-", ""));
        apiKey.setApiSecret("ss_" + UUID.randomUUID().toString().replace("-", ""));
        apiKey.setStatus(1);
        apiKey.setExpireDate(LocalDate.now().plusYears(1));
        
        apiKeyMapper.insert(apiKey);
        return apiKey;
    }
    
    public List<ApiKey> getUserApiKeys(Long userId) {
        return apiKeyMapper.findByUserId(userId);
    }
    
    public ApiKey getByApiKey(String apiKey) {
        return apiKeyMapper.findByApiKey(apiKey);
    }
    
    public void revokeApiKey(Long id) {
        ApiKey apiKey = apiKeyMapper.findById(id);
        if (apiKey != null) {
            apiKey.setStatus(0);
            apiKeyMapper.update(apiKey);
        }
    }
    
    public boolean validateApiKey(String apiKeyStr) {
        ApiKey apiKey = apiKeyMapper.findByApiKey(apiKeyStr);
        if (apiKey == null || apiKey.getStatus() == 0) {
            return false;
        }
        
        // 检查是否过期
        if (apiKey.getExpireDate() != null && apiKey.getExpireDate().isBefore(LocalDate.now())) {
            return false;
        }
        
        return true;
    }
}
