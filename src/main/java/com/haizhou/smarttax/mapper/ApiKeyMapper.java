package com.haizhou.smarttax.mapper;

import com.haizhou.smarttax.entity.ApiKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ApiKeyMapper {
    ApiKey findById(@Param("id") Long id);
    ApiKey findByApiKey(@Param("apiKey") String apiKey);
    List<ApiKey> findByUserId(@Param("userId") Long userId);
    int insert(ApiKey apiKey);
    int update(ApiKey apiKey);
    int deleteById(@Param("id") Long id);
}
