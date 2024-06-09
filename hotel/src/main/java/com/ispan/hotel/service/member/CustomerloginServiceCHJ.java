package com.ispan.hotel.service.member;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.MemberRank;
import com.ispan.hotel.repository.member.CustomerloginRepositoryCHJ;
import com.ispan.hotel.repository.member.MemberRankRepositoryCHJ;
import com.ispan.hotel.util.DatetimeConverter;

@Service
public class CustomerloginServiceCHJ {
    @Autowired
    private CustomerloginRepositoryCHJ customerloginRepositoryCHJ;

    @Autowired
    private MemberRankRepositoryCHJ memberRankRepository;

    @Autowired
    private EmailServiceCHJ emailService;
    @Autowired
    private Map<String, Boolean> sentVerificationEmails = new HashMap<>();

    public void sendVerificationEmail(String email) {
        if (sentVerificationEmails.containsKey(email) && sentVerificationEmails.get(email)) {
            return;
        }
        Optional<Customer> customerOpt = customerloginRepositoryCHJ.findByEmail(email);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            // 生成驗證令牌
            String verificationToken = UUID.randomUUID().toString();
            // 設置驗證令牌的過期時間為當前時間加上二分鐘
            LocalDateTime expirationTime = LocalDateTime.now().plus(2, ChronoUnit.MINUTES);
            // 將驗證令牌和過期時間保存到用戶對象中
            customer.setVerificationToken(verificationToken);
            customer.setVerificationTokenExpiration(expirationTime);
            // 更新用戶信息
            customerloginRepositoryCHJ.save(customer);

            // 驗證連結
            String verificationLink = "http://localhost:5173/pages/verificationSuccess?email=" + email + "&token="
                    + verificationToken;
            // 設置郵件主題
            String subject = "請驗證您的電子郵件地址";
            // 構建HTML格式內容，包括驗證連結和有效期限
            String htmlContent = "<p>請點擊以下鏈接驗證您的電子郵件地址:</p>"
                    + "<p><a href=\"" + verificationLink
                    + "\" style=\"color: white; background-color: red; padding: 10px; text-decoration: none; border-radius: 5px;\">驗證郵件</a></p>"
                    + "<p>此驗證鏈接將在 " + DatetimeConverter.toString(expirationTime, "yyyy-MM-dd HH:mm:ss") + " 過期。</p>";

            // 發送HTML格式郵件
            emailService.sendHtmlEmail(email, "芳山旅館💎", subject, htmlContent);
            sentVerificationEmails.put(email, true);
        }
    }

    public void updateMemberStatus(String email, String status) {
        Optional<Customer> customerOpt = customerloginRepositoryCHJ.findByEmail(email);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.setMemberStatus(status);
            customerloginRepositoryCHJ.save(customer);
        }
    }

    public Customer registerCustomer(Customer customer) {
        customer.setMemberStatus("none"); // 預設會員狀態為 none
        return customerloginRepositoryCHJ.save(customer);
    }

    public Optional<Customer> findByUsername(String username) {
        return customerloginRepositoryCHJ.findByUsername(username);
    }

    public Optional<Customer> findByEmail(String email) {
        return customerloginRepositoryCHJ.findByEmail(email);
    }

    public boolean hasSentVerificationEmail(String email) {
        Optional<Customer> customerOpt = customerloginRepositoryCHJ.findByEmail(email);
        return customerOpt.map(Customer::getVerificationToken).isPresent();

    }

    public Customer updateCustomer(Customer customer) {
        return customerloginRepositoryCHJ.save(customer);
    }

    public Optional<MemberRank> findMemberRankById(Integer id) {
        return memberRankRepository.findById(id);
    }

    public boolean verifyCode(String email, String enteredCode) {
        String savedCode = getSavedVerificationCode(email);
        return enteredCode.equals(savedCode);
    }

    private String getSavedVerificationCode(String email) {
        // 從數據庫查找並返回驗證碼
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

    public void sendVerificationCode(String email) {
        if (emailExists(email)) {
            String verificationCode = generateRandomCode();
            emailService.sendVerificationCode(email, verificationCode);
            saveVerificationCode(email, verificationCode);
        }
    }

    public Customer save(Customer customer) {
        return customerloginRepositoryCHJ.save(customer);
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
}
