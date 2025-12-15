package com.haizhou.smarttax.mapper;

import com.haizhou.smarttax.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findById(@Param("id") Long id);
    User findByEmail(@Param("email") String email);
    int insert(User user);
    int update(User user);
    int deleteById(@Param("id") Long id);
}
