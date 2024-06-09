package com.ispan.hotel.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.ispan.hotel.model.Customer;
import com.ispan.hotel.repository.member.CustomerloginRepositoryCHJ;

import java.util.Random;

@Service
public class ForgotPasswordServiceCHJ {

    @Autowired
    private EmailServiceCHJ emailService;

    @Autowired
    private CustomerloginRepositoryCHJ customerloginRepositoryCHJ;

    public void sendVerificationCode(String email) {
        if (emailExists(email)) {
            String verificationCode = generateRandomCode();
            emailService.sendVerificationCode(email, verificationCode);
            saveVerificationCode(email, verificationCode);
        } else {

        }
    }

    public boolean verifyCode(String email, String enteredCode) {
        String savedCode = getSavedVerificationCode(email);
        return enteredCode.equals(savedCode);
    }

    private String generateRandomCode() {
        // 生成隨機六位數驗證碼
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String getSavedVerificationCode(String email) {
        // 從資料庫查找並返回自己的驗證碼
        Optional<Customer> customerOptional = customerloginRepositoryCHJ.findByEmail(email);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return customer.getVerificationCode();
        } else {
            return null;
        }
    }

    private void saveVerificationCode(String email, String verificationCode) {
        Optional<Customer> customerOptional = customerloginRepositoryCHJ.findByEmail(email);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setVerificationCode(verificationCode);

            customerloginRepositoryCHJ.save(customer);
        }
    }

    public boolean emailExists(String email) {
        Optional<Customer> customerOptional = customerloginRepositoryCHJ.findByEmail(email);
        return customerOptional.isPresent();
    }
}
