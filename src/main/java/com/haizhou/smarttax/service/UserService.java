package com.haizhou.smarttax.service;

import com.haizhou.smarttax.dto.LoginRequest;
import com.haizhou.smarttax.dto.RegisterRequest;
import com.haizhou.smarttax.entity.User;
import com.haizhou.smarttax.mapper.UserMapper;
import com.haizhou.smarttax.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Map<String, Object> register(RegisterRequest request) {
        // 检查邮箱是否已存在
        User existUser = userMapper.findByEmail(request.getEmail());
        if (existUser != null) {
            throw new RuntimeException("该邮箱已被注册");
        }
        
        // 创建新用户
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCompanyName(request.getCompanyName());
        user.setPhone(request.getPhone());
        user.setStatus(1);
        
        userMapper.insert(user);
        
        // 生成token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", hidePassword(user));
        return result;
    }
    
    public Map<String, Object> login(LoginRequest request) {
        User user = userMapper.findByEmail(request.getEmail());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        
        if (user.getStatus() == 0) {
            throw new RuntimeException("账户已被禁用");
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", hidePassword(user));
        return result;
    }
    
    public User getUserById(Long id) {
        User user = userMapper.findById(id);
        return hidePassword(user);
    }
    
    private User hidePassword(User user) {
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }
}
