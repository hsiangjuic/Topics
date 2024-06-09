package com.ispan.hotel.service.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Customer;
import com.ispan.hotel.repository.member.LoginRepositoryCHJ;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class LoginServiceCHJ {
    @Autowired
    private LoginRepositoryCHJ loginRepositoryCHJ;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateLogin(String username, String password) {
        Optional<Customer> customerOptional = loginRepositoryCHJ.findByUsername(username);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            String storedPassword = customer.getPassword();

            // 使用PasswordEncoder對用戶提供的密碼進行加密
            if (passwordEncoder.matches(password, storedPassword)) {
                // false帳號被鎖，none 信箱未驗證
                if (!"false".equals(customer.getMemberStatus()) && !"none".equals(customer.getMemberStatus())) {
                    return true; // 登入成功
                }
            }
        }
        return false; // 登入失敗或帳號被鎖或信箱未驗證
    }
}
