package com.haizhou.smarttax.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ExchangeRateRequest {
    @NotBlank(message = "源币种不能为空")
    private String fromCurrency;
    
    @NotBlank(message = "目标币种不能为空")
    private String toCurrency;
    
    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    public String getFromCurrency() { return fromCurrency; }
    public void setFromCurrency(String fromCurrency) { this.fromCurrency = fromCurrency; }
    public String getToCurrency() { return toCurrency; }
    public void setToCurrency(String toCurrency) { this.toCurrency = toCurrency; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
