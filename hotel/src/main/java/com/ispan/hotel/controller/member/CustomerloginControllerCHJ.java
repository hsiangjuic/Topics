package com.ispan.hotel.controller.member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.MemberRank;
import com.ispan.hotel.service.member.CustomerloginServiceCHJ;
// import com.ispan.hotel.util.DatetimeConverter;
// import com.ispan.hotel.service.member.EmailServiceCHJ;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class CustomerloginControllerCHJ {
    @Autowired
    private CustomerloginServiceCHJ customerloginServiceCHJ;
    // 監控錯誤信息日誌Logger logger
    private static final Logger logger = Logger.getLogger(CustomerloginControllerCHJ.class.getName());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String registerRequest) {
        JSONObject request = new JSONObject(registerRequest);
        Customer customer = new Customer();
        customer.setFirstName(request.getString("firstName"));
        customer.setLastName(request.getString("lastName"));
        customer.setUsername(request.getString("username"));
        customer.setIdentification(request.optString("identification", null));
        customer.setPassportNumber(request.optString("passportNumber", null));
        customer.setTel(request.getString("tel"));
        customer.setAddress(request.getString("address"));
        customer.setBirthday(LocalDate.parse(request.getString("birthday")));
        customer.setGender(request.getString("gender"));
        customer.setCountry(request.getString("country"));
        customer.setEmail(request.getString("email"));

        // customer.setPassword(request.getString("password"));
        String encodedPassword = passwordEncoder.encode(request.getString("password"));
        customer.setPassword(encodedPassword);
        customer.setRemark(request.optString("remark", null));
        customer.setMemberStatus(request.getString("memberStatus"));
        customer.setTotalPoints(request.optInt("totalPoints", 0));
        customer.setAllowPromotionMail(request.optString("allowPromotionMail", null));
        Integer mrId = request.isNull("mrId") ? null : request.getInt("mrId");
        if (mrId != null && mrId != 0) {
            Optional<MemberRank> optionalMemberRank = customerloginServiceCHJ.findMemberRankById(mrId);
            optionalMemberRank.ifPresent(customer::setMemberRank);
        } else {
            customer.setMemberRank(null);
        }
        String token = UUID.randomUUID().toString();
        customer.setVerificationToken(token);

        customerloginServiceCHJ.registerCustomer(customer);
        customerloginServiceCHJ.sendVerificationEmail(customer.getEmail());

        customerloginServiceCHJ.registerCustomer(customer);

        // （如果是首次註冊）
        if (!customerloginServiceCHJ.hasSentVerificationEmail(customer.getEmail())) {
            customerloginServiceCHJ.sendVerificationEmail(customer.getEmail());
        }

        return ResponseEntity.ok("{\"message\":\"註冊成功\"}");
    }

    @GetMapping("/{username}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        Optional<Customer> customer = customerloginServiceCHJ.findByUsername(username);

        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mrId/{username}")
    public ResponseEntity<Integer> getMrIdByUsername(@PathVariable String username) {
        Optional<Customer> customer = customerloginServiceCHJ.findByUsername(username);
        if (customer.isPresent()) {
            MemberRank memberRank = customer.get().getMemberRank();
            if (memberRank != null) {
                return ResponseEntity.ok(memberRank.getMrId());
            } else {
                return ResponseEntity.ok(null); // or ResponseEntity.status(404).body(null);
            }
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestBody String updateRequest) {
        JSONObject request = new JSONObject(updateRequest);
        String username = request.getString("username");
        Optional<Customer> optionalCustomer = customerloginServiceCHJ.findByUsername(username);

        if (optionalCustomer.isPresent()) {

            Customer customer = optionalCustomer.get();
            customer.setFirstName(request.getString("firstName"));
            customer.setLastName(request.getString("lastName"));
            customer.setIdentification(request.optString("identification", null));
            customer.setPassportNumber(request.optString("passportNumber", null));
            customer.setTel(request.getString("tel"));
            customer.setAddress(request.getString("address"));
            customer.setBirthday(LocalDate.parse(request.getString("birthday")));
            customer.setGender(request.getString("gender"));
            customer.setCountry(request.getString("country"));
            customer.setEmail(request.getString("email"));
            customer.setBeginDate(LocalDate.parse(request.getString("beginDate")));
            customer.setRemark(request.optString("remark", null));
            customer.setMemberStatus(request.getString("memberStatus"));
            customer.setTotalPoints(request.optInt("totalPoints", 0));
            customer.setAllowPromotionMail(request.optString("allowPromotionMail", null));

            Integer mrId = request.isNull("mrId") ? null : request.getInt("mrId");
            if (mrId != null) {
                Optional<MemberRank> optionalMemberRank = customerloginServiceCHJ.findMemberRankById(mrId);
                if (optionalMemberRank.isPresent()) {
                    customer.setMemberRank(optionalMemberRank.get());
                }
            } else {
                customer.setMemberRank(null);
            }

            customerloginServiceCHJ.updateCustomer(customer);

            return ResponseEntity.ok("{\"message\":\"更新成功\"}");
        } else {
            return ResponseEntity.status(404).body("{\"message\":\"用戶不存在\"}");
        }
    }

    @PostMapping("/sendVerificationEmail")
    public ResponseEntity<?> sendVerificationEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        customerloginServiceCHJ.sendVerificationEmail(email);
        return ResponseEntity.ok("驗證郵件已發送");
    }

    @PostMapping("/updateMemberStatus")
    public ResponseEntity<?> updateMemberStatus(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String status = request.get("status");
        customerloginServiceCHJ.updateMemberStatus(email, status);
        return ResponseEntity.ok("會員狀態已更新");
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String email, @RequestParam String token) {
        Optional<Customer> customerOpt = customerloginServiceCHJ.findByEmail(email);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            logger.info("Received email: " + email); //
            logger.info("Received token: " + token); //
            if (token.equals(customer.getVerificationToken())
                    && LocalDateTime.now().isBefore(customer.getVerificationTokenExpiration())) {
                logger.info("Verification token matched and is not expired"); //
                // 驗證通過
                customerloginServiceCHJ.updateMemberStatus(email, "true");
                return ResponseEntity.ok("電子郵件驗證成功");
            } else {
                logger.info("Verification token not matched or expired"); // 添加日志语句
                // 令牌無效或已過期
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("電子郵件驗證失敗");
            }
        } else {
            // 未找到相應用戶
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("電子郵件驗證失敗");
        }
    }

}
