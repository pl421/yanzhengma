package com.yanzhengma.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:5173") 
public class CaptchaController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String CAPTCHA_PREFIX = "captcha:"; // Redis key 前缀
    private static final long CAPTCHA_EXPIRE_MINUTES = 5;   // 过期时间：5分钟

    /**
     * 生成图形验证码（返回 Base64 图片 + UUID）
     */
    @GetMapping("/captcha")
    public Map<String, String> generateCaptcha() throws Exception {
        // 1. 生成4位随机数字
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(10));
        }
        String captchaText = code.toString();

        // 2. 生成唯一 key
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String redisKey = CAPTCHA_PREFIX + uuid;

        // 3. 存入 Redis，5分钟过期
        redisTemplate.opsForValue().set(redisKey, captchaText, Duration.ofMinutes(CAPTCHA_EXPIRE_MINUTES));

        // 4. 绘制图片
        BufferedImage image = createImage(captchaText);

        // 5. 转为 Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());

        // 6. 返回给前端
        Map<String, String> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("image", base64Image);
        return result;
    }

    /**
     * 验证用户输入的验证码
     */
    @PostMapping("/verify")
    public boolean verifyCaptcha(@RequestBody Map<String, String> request) {
        String uuid = request.get("uuid");
        String userInput = request.get("code");

        if (uuid == null || userInput == null) {
            return false;
        }

        String redisKey = CAPTCHA_PREFIX + uuid;
        String realCode = redisTemplate.opsForValue().get(redisKey);

        // 注意：验证码通常区分大小写？这里数字不区分，但建议保持一致
        return realCode != null && realCode.equals(userInput.trim());
        // 可选：验证后立即删除（防重放）→ 用 getAndDelete
        // String realCode = redisTemplate.opsForValue().getAndDelete(redisKey);
    }

    // ------------------ 工具方法：绘制验证码图片 ------------------
    private BufferedImage createImage(String code) {
        int width = 120;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 边框
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);

        // 随机字体颜色 + 绘制字符
        Random random = new Random();
        g.setFont(new Font("Arial", Font.BOLD, 32));
        for (int i = 0; i < code.length(); i++) {
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.drawString(String.valueOf(code.charAt(i)), 25 * i + 10, 30);
        }

        // 干扰线
        g.setColor(Color.GRAY);
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

        g.dispose();
        return image;
    }
}
