package com.yanzhengma.demo.dto;
import lombok.Data;
@Data
public class RegisterDTO {
	private String userId;
    private String userName;
    private String password;
    private Integer gender; 
    private String phone;
    private String email;
    private String captchaInput;   // ← 临时字段，只用于校验
    private String uuid;      // ← 临时字段，只用于查 Redis
}