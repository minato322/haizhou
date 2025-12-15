package com.haizhou.smarttax.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxDeclarationRequest {
    private String declarationType;
    
    @NotNull(message = "申报金额不能为空")
    private BigDecimal amount;
    
    private BigDecimal taxAmount;
    private String currency;
    private LocalDate declarationDate;
    private String remarks;

    public String getDeclarationType() { return declarationType; }
    public void setDeclarationType(String declarationType) { this.declarationType = declarationType; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public LocalDate getDeclarationDate() { return declarationDate; }
    public void setDeclarationDate(LocalDate declarationDate) { this.declarationDate = declarationDate; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
