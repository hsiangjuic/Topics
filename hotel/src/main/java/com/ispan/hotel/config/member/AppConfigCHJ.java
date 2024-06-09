package com.ispan.hotel.config.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfigCHJ {
    // 不能重複寄信(沒寫會有BUG)
    @Bean
    public Map<String, Boolean> sentVerificationEmails() {
        return new HashMap<>();
    }

    // SSL加密
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
