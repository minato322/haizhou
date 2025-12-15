package com.haizhou.smarttax.mapper;

import com.haizhou.smarttax.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ChatMessageMapper {
    List<ChatMessage> findByUserId(@Param("userId") Long userId);
    int insert(ChatMessage message);
    int updateReadStatus(@Param("id") Long id, @Param("isRead") Integer isRead);
}
