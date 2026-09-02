//package com.sky;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import java.util.Map;
//
//public class PasswordGenerator {
//
//    public static void main(String[] args) {
//        // 1. 构造和项目一模一样的编码器（推荐复制项目中的配置代码）
//        String defaultId = "bcrypt";
//        Map<String, PasswordEncoder> encoders = Map.of(
//                defaultId, new BCryptPasswordEncoder(12) // 强度保持12
//        );
//        PasswordEncoder encoder = new DelegatingPasswordEncoder(defaultId, encoders);
//
//        // 2. 生成密码 "123456" 的密文
//        String encodedPassword = encoder.encode("123456");
//
//        // 3. 打印结果，复制控制台输出的完整字符串
//        System.out.println("生成的密文: " + encodedPassword);
//        System.out.println("长度: " + encodedPassword.length());
//    }
//}