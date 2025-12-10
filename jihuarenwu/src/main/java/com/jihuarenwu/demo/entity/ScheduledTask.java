package com.jihuarenwu.demo.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("scheduled_task")
public class ScheduledTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskName;
    private String taskGroup;
    private String cronExpression;
    private String status; // "ACTIVE", "PAUSED"
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}