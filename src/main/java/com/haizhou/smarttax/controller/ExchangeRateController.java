package com.haizhou.smarttax.controller;

import com.haizhou.smarttax.common.Result;
import com.haizhou.smarttax.dto.ExchangeRateRequest;
import com.haizhou.smarttax.entity.ExchangeRate;
import com.haizhou.smarttax.service.ExchangeRateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/exchange")
public class ExchangeRateController {
    
    @Autowired
    private ExchangeRateService rateService;
    
    @PostMapping("/convert")
    public Result<Map<String, Object>> convert(@Valid @RequestBody ExchangeRateRequest request) {
        Map<String, Object> result = rateService.convert(
            request.getFromCurrency(),
            request.getToCurrency(),
            request.getAmount()
        );
        return Result.success(result);
    }
    
    @GetMapping("/rate")
    public Result<ExchangeRate> getRate(
            @RequestParam String from,
            @RequestParam String to) {
        
        ExchangeRate rate = rateService.getRate(from, to);
        return Result.success(rate);
    }
}
