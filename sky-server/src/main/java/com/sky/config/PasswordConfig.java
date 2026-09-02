//package com.sky.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
//import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
//
//import java.util.Map;
//
//@Configuration
//public class PasswordConfig {
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        //指定默认的密码编码器id[reference:1]
//        String defaultId ="bcrypt";
//        //创建一个Map，包含所有你希望支持的编码器[reference:2][reference:3]
//        Map<String, PasswordEncoder> encoders= Map.of(
//                defaultId,new BCryptPasswordEncoder(12),
//                "pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8(),
//                "scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8(),
//                "argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
//        );
//        // 3. 创建 DelegatingPasswordEncoder[reference:5][reference:6]
//        return new DelegatingPasswordEncoder(defaultId,encoders);
//    }
//}
