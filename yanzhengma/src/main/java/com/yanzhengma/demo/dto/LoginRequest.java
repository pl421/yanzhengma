package com.yanzhengma.demo.dto;

import lombok.Data;
@Data
public class LoginRequest {
    private String userName;
    private String password;
    private String captchaInput;   // ← 临时字段，只用于校验
    private String uuid;      // ← 临时字段，只用于查 Redis
}
