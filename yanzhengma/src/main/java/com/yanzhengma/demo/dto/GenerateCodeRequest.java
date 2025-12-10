package com.yanzhengma.demo.dto;

import java.util.List;

import lombok.Data;
@Data
public class GenerateCodeRequest {

    private String schema;
    private List<String> tableNames;
}

