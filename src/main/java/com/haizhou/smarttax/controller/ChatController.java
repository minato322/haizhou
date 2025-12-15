package com.haizhou.smarttax.controller;

import com.haizhou.smarttax.common.Result;
import com.haizhou.smarttax.entity.ChatMessage;
import com.haizhou.smarttax.service.ChatMessageService;
import com.haizhou.smarttax.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    
    @Autowired
    private ChatMessageService messageService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/message")
    public Result<ChatMessage> sendMessage(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, String> payload) {
        
        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(token);
        
        String content = payload.get("content");
        // 保存用户消息
        ChatMessage userMessage = messageService.sendMessage(userId, "USER", content);
        
        // 自动回复，返回AI的回复消息
        ChatMessage aiReply = messageService.autoReply(userId, content);
        
        return Result.success(aiReply);
    }
    
    @GetMapping("/messages")
    public Result<List<ChatMessage>> getMessages(
            @RequestHeader("Authorization") String authorization) {
        
        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(token);
        
        List<ChatMessage> messages = messageService.getUserMessages(userId);
        return Result.success(messages);
    }
    
    @PutMapping("/message/{id}/read")
    public Result<Void> markAsRead(@PathVariable Long id) {
        messageService.markAsRead(id);
        return Result.success();
    }
}
