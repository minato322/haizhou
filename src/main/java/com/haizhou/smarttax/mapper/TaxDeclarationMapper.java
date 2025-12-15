package com.haizhou.smarttax.mapper;

import com.haizhou.smarttax.entity.TaxDeclaration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TaxDeclarationMapper {
    TaxDeclaration findById(@Param("id") Long id);
    List<TaxDeclaration> findByUserId(@Param("userId") Long userId);
    List<TaxDeclaration> findAll();
    int insert(TaxDeclaration declaration);
    int update(TaxDeclaration declaration);
    int deleteById(@Param("id") Long id);
}
