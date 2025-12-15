package com.haizhou.smarttax.service;

import com.haizhou.smarttax.dto.TaxDeclarationRequest;
import com.haizhou.smarttax.entity.TaxDeclaration;
import com.haizhou.smarttax.mapper.TaxDeclarationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaxDeclarationService {
    
    @Autowired
    private TaxDeclarationMapper declarationMapper;
    
    public TaxDeclaration createDeclaration(Long userId, TaxDeclarationRequest request) {
        TaxDeclaration declaration = new TaxDeclaration();
        declaration.setUserId(userId);
        declaration.setDeclarationType(request.getDeclarationType());
        declaration.setAmount(request.getAmount());
        declaration.setTaxAmount(request.getTaxAmount());
        declaration.setCurrency(request.getCurrency() != null ? request.getCurrency() : "CNY");
        declaration.setStatus("PENDING");
        declaration.setDeclarationDate(request.getDeclarationDate());
        declaration.setRemarks(request.getRemarks());
        
        declarationMapper.insert(declaration);
        return declaration;
    }
    
    public List<TaxDeclaration> getUserDeclarations(Long userId) {
        return declarationMapper.findByUserId(userId);
    }
    
    public TaxDeclaration getDeclarationById(Long id) {
        return declarationMapper.findById(id);
    }
    
    public TaxDeclaration updateDeclarationStatus(Long id, String status) {
        TaxDeclaration declaration = declarationMapper.findById(id);
        if (declaration == null) {
            throw new RuntimeException("申报记录不存在");
        }
        
        declaration.setStatus(status);
        declaration.setAuditDate(LocalDateTime.now());
        declarationMapper.update(declaration);
        
        return declaration;
    }
    
    public List<TaxDeclaration> getAllDeclarations() {
        return declarationMapper.findAll();
    }
}
