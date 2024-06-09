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
            saveVerificationCode(email, verificationCode); // Save the verification code
        } else {
            // 如果找不到该 email，可以根据需要执行一些操作，比如返回错误消息给用户
        }
    }

    public boolean verifyCode(String email, String enteredCode) {
        String savedCode = getSavedVerificationCode(email);
        return enteredCode.equals(savedCode);
    }

    private String generateRandomCode() {
        // 生成随机的六位数验证码
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String getSavedVerificationCode(String email) {
        // 从数据库中查找并返回之前保存的验证码
        Optional<Customer> customerOptional = customerloginRepositoryCHJ.findByEmail(email);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return customer.getVerificationCode(); // Assuming the verification code is stored in the Customer entity
        } else {
            return null; // No verification code found for the given email
        }
    }

    private void saveVerificationCode(String email, String verificationCode) {
        // Save the verification code to the database
        Optional<Customer> customerOptional = customerloginRepositoryCHJ.findByEmail(email);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setVerificationCode(verificationCode); // Assuming the verification code is stored in the Customer
                                                            // entity
            customerloginRepositoryCHJ.save(customer); // Save the updated customer entity
        } else {
            // Handle the case where the customer does not exist
        }
    }

    public boolean emailExists(String email) {
        Optional<Customer> customerOptional = customerloginRepositoryCHJ.findByEmail(email);
        return customerOptional.isPresent();
    }
}
