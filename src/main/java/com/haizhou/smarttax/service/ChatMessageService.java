package com.haizhou.smarttax.service;

import com.haizhou.smarttax.entity.ChatMessage;
import com.haizhou.smarttax.mapper.ChatMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatMessageService {
    
    @Autowired
    private ChatMessageMapper messageMapper;
    
    @Autowired
    private DeepSeekService deepSeekService;
    
    public ChatMessage sendMessage(Long userId, String senderType, String content) {
        ChatMessage message = new ChatMessage();
        message.setUserId(userId);
        message.setSenderType(senderType);
        message.setMessageContent(content);
        message.setIsRead(0);
        
        messageMapper.insert(message);
        return message;
    }
    
    public List<ChatMessage> getUserMessages(Long userId) {
        return messageMapper.findByUserId(userId);
    }
    
    public void markAsRead(Long messageId) {
        messageMapper.updateReadStatus(messageId, 1);
    }
    
    /**
     * AI智能回复（使用DeepSeek）
     */
    public ChatMessage autoReply(Long userId, String userMessage) {
        try {
            // 获取最近的对话历史（最多10条），但不包括刚刚发送的这条用户消息
            List<ChatMessage> history = getUserMessages(userId);
            List<Map<String, String>> conversationHistory = new ArrayList<>();
            
            // 构建对话历史（排除最后一条，即当前用户消息）
            int historySize = history.size();
            if (historySize > 0) {
                // 只取最近的10条历史消息（不包括当前这条）
                int startIndex = Math.max(0, historySize - 11); // 多取1条，因为最后一条是当前消息
                int endIndex = historySize - 1; // 排除最后一条（当前用户消息）
                
                for (int i = startIndex; i < endIndex; i++) {
                    ChatMessage msg = history.get(i);
                    Map<String, String> historyMsg = new HashMap<>();
                    historyMsg.put("role", msg.getSenderType().equals("USER") ? "user" : "assistant");
                    historyMsg.put("content", msg.getMessageContent());
                    conversationHistory.add(historyMsg);
                }
            }
            
            // 调用DeepSeek AI获取回复
            String aiReply = deepSeekService.chat(userMessage, conversationHistory);
            
            // 保存AI回复到数据库
            return sendMessage(userId, "AGENT", aiReply);
            
        } catch (Exception e) {
            System.err.println("AI回复失败，使用备用回复: " + e.getMessage());
            e.printStackTrace();
            // 如果AI调用失败，使用简单的规则回复
            String reply = deepSeekService.chat(userMessage);
            return sendMessage(userId, "AGENT", reply);
        }
    }
}
