package com.haizhou.smarttax.controller;

import com.haizhou.smarttax.common.Result;
import com.haizhou.smarttax.dto.TaxDeclarationRequest;
import com.haizhou.smarttax.entity.TaxDeclaration;
import com.haizhou.smarttax.service.TaxDeclarationService;
import com.haizhou.smarttax.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tax")
public class TaxDeclarationController {
    
    @Autowired
    private TaxDeclarationService declarationService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/declaration")
    public Result<TaxDeclaration> createDeclaration(
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody TaxDeclarationRequest request) {
        
        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(token);
        
        TaxDeclaration declaration = declarationService.createDeclaration(userId, request);
        return Result.success(declaration);
    }
    
    @GetMapping("/declarations")
    public Result<List<TaxDeclaration>> getUserDeclarations(
            @RequestHeader("Authorization") String authorization) {
        
        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(token);
        
        List<TaxDeclaration> declarations = declarationService.getUserDeclarations(userId);
        return Result.success(declarations);
    }
    
    @GetMapping("/declaration/{id}")
    public Result<TaxDeclaration> getDeclaration(@PathVariable Long id) {
        TaxDeclaration declaration = declarationService.getDeclarationById(id);
        return Result.success(declaration);
    }
    
    @PutMapping("/declaration/{id}/status")
    public Result<TaxDeclaration> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        
        TaxDeclaration declaration = declarationService.updateDeclarationStatus(id, status);
        return Result.success(declaration);
    }
}
