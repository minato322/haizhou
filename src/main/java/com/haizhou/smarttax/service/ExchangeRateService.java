package com.haizhou.smarttax.service;

import com.haizhou.smarttax.entity.ExchangeRate;
import com.haizhou.smarttax.mapper.ExchangeRateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService {
    
    @Autowired
    private ExchangeRateMapper rateMapper;
    
    public Map<String, Object> convert(String fromCurrency, String toCurrency, BigDecimal amount) {
        if (fromCurrency.equals(toCurrency)) {
            Map<String, Object> result = new HashMap<>();
            result.put("fromCurrency", fromCurrency);
            result.put("toCurrency", toCurrency);
            result.put("amount", amount);
            result.put("result", amount);
            result.put("rate", BigDecimal.ONE);
            return result;
        }
        
        ExchangeRate rate = rateMapper.findByLatest(fromCurrency, toCurrency);
        if (rate == null) {
            throw new RuntimeException("汇率数据不存在");
        }
        
        BigDecimal convertedAmount = amount.multiply(rate.getRate()).setScale(2, RoundingMode.HALF_UP);
        
        Map<String, Object> result = new HashMap<>();
        result.put("fromCurrency", fromCurrency);
        result.put("toCurrency", toCurrency);
        result.put("amount", amount);
        result.put("result", convertedAmount);
        result.put("rate", rate.getRate());
        result.put("rateDate", rate.getRateDate());
        
        return result;
    }
    
    public ExchangeRate getRate(String fromCurrency, String toCurrency) {
        return rateMapper.findByLatest(fromCurrency, toCurrency);
    }
}
