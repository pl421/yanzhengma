package com.yanzhengma.demo.dto;


import lombok.Data;

@Data
public class SyncRequest {
    private Long userId;
    private String action; // e.g., "login", "logout"
}