//package com.yanzhengma.demo.client;
//
//
//import com.yanzhengma.demo.dto.SyncRequest;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
///**
// * 声明式 Feign 客户端，用于调用 user-service 微服务。
// * 注意：
// *   - name = "user-service" 必须与目标服务的 spring.application.name 一致
// *   - 接口路径必须与目标 Controller 的 @PostMapping 路径完全匹配
// */
//@FeignClient(name = "user-service")
//public interface UserServiceClient {
//
//    /**
//     * 向 user-service 发送用户行为同步请求
//     *
//     * @param request 包含 userId 和 action 的同步数据
//     */
//    @PostMapping("/api/user/sync")
//    void syncUserAction(@RequestBody SyncRequest request);
//}