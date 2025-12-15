-- 创建数据库
CREATE DATABASE IF NOT EXISTS smart_tax_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE smart_tax_db;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    company_name VARCHAR(200) COMMENT '公司名称',
    phone VARCHAR(20) COMMENT '联系电话',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_email (email),
    INDEX idx_company (company_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 税务申报表
CREATE TABLE IF NOT EXISTS tax_declaration (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '申报ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    declaration_type VARCHAR(50) COMMENT '申报类型',
    amount DECIMAL(15, 2) COMMENT '申报金额',
    tax_amount DECIMAL(15, 2) COMMENT '退税金额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '币种',
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态：PENDING-待审核 APPROVED-已批准 REJECTED-已拒绝',
    declaration_date DATE COMMENT '申报日期',
    audit_date DATETIME COMMENT '审核日期',
    remarks TEXT COMMENT '备注',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES user(id),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_declaration_date (declaration_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='税务申报表';

-- 汇率表
CREATE TABLE IF NOT EXISTS exchange_rate (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '汇率ID',
    from_currency VARCHAR(10) NOT NULL COMMENT '源币种',
    to_currency VARCHAR(10) NOT NULL COMMENT '目标币种',
    rate DECIMAL(15, 6) NOT NULL COMMENT '汇率',
    rate_date DATE NOT NULL COMMENT '汇率日期',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_currency_date (from_currency, to_currency, rate_date),
    INDEX idx_date (rate_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='汇率表';

-- 客服消息表
CREATE TABLE IF NOT EXISTS chat_message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '消息ID',
    user_id BIGINT COMMENT '用户ID',
    sender_type VARCHAR(20) NOT NULL COMMENT '发送者类型：USER-用户 AGENT-客服',
    message_content TEXT NOT NULL COMMENT '消息内容',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读：0-未读 1-已读',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (user_id) REFERENCES user(id),
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客服消息表';

-- API密钥表
CREATE TABLE IF NOT EXISTS api_key (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'API密钥ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    api_key VARCHAR(255) NOT NULL UNIQUE COMMENT 'API密钥',
    api_secret VARCHAR(255) NOT NULL COMMENT 'API密钥',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    expire_date DATE COMMENT '过期日期',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES user(id),
    INDEX idx_user_id (user_id),
    INDEX idx_api_key (api_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='API密钥表';

-- 插入测试汇率数据
INSERT INTO exchange_rate (from_currency, to_currency, rate, rate_date) VALUES
('CNY', 'USD', 0.14, CURDATE()),
('CNY', 'JPY', 21.8, CURDATE()),
('CNY', 'EUR', 0.13, CURDATE()),
('CNY', 'KRW', 178.5, CURDATE()),
('CNY', 'SGD', 0.19, CURDATE()),
('USD', 'CNY', 7.2, CURDATE()),
('USD', 'JPY', 156.5, CURDATE()),
('USD', 'EUR', 0.92, CURDATE()),
('USD', 'KRW', 1278.5, CURDATE()),
('USD', 'SGD', 1.36, CURDATE());
