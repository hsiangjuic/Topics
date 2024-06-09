package com.ispan.hotel.controller.member;

import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ispan.hotel.model.Customer;
// import com.ispan.hotel.model.MemberRank;
import com.ispan.hotel.service.member.CustomerloginServiceCHJ;
import com.ispan.hotel.service.member.ForgotPasswordServiceCHJ;
import com.ispan.hotel.util.DatetimeConverter;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class ForgotPasswordControllerCHJ {
    @Autowired
    private ForgotPasswordServiceCHJ forgotPasswordService;

    @Autowired
    private CustomerloginServiceCHJ customerloginServiceCHJ;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 用戶請求發送驗證碼，使用 GET 请求
    @GetMapping("/forgotpassword") // 使用 GetMapping
    public JSONObject sendVerificationCode(@RequestParam String email) {
        JSONObject response = new JSONObject();
        try {
            // 檢查電子郵件是否存在
            if (forgotPasswordService.emailExists(email)) {
                forgotPasswordService.sendVerificationCode(email);
                response.put("success", true);
            } else {
                response.put("success", false);
                response.put("message", "Email does not exist!"); // 電子郵件不存在
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to send verification code!");
        }
        return response;
    }

    @PostMapping("/forgotpassword/send-verification-code")
    public JSONObject sendVerificationCode(@RequestBody Map<String, String> requestBody) {
        JSONObject response = new JSONObject();
        String email = requestBody.get("email");
        try {
            // 檢查電子郵件是否發送驗證碼
            if (forgotPasswordService.emailExists(email)) {
                forgotPasswordService.sendVerificationCode(email);
                response.put("success", true);
            } else {
                response.put("success", false);
                response.put("message", "Email does not exist!"); // 電子郵件不存在
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to send verification code!");
        }
        return response;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String email) {
        Optional<Customer> customer = customerloginServiceCHJ.findByEmail(email);

        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 用戶驗證驗證碼
    @PostMapping("/verifycode")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> requestBody) {
        try {
            String email = requestBody.get("email");
            String enteredCode = requestBody.get("enteredCode");
            boolean isVerified = customerloginServiceCHJ.verifyCode(email, enteredCode);
            return ResponseEntity.ok().body(isVerified);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to verify code!");
        }
    }

    @PutMapping("/updatepassword/{email}")
    public ResponseEntity<String> update(@PathVariable String email, @RequestBody String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Optional<Customer> optionalCustomer = customerloginServiceCHJ.findByEmail(email);
            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();

                // 更新可，如果字段存在
                if (obj.has("firstName"))
                    customer.setFirstName(obj.getString("firstName"));
                if (obj.has("lastName"))
                    customer.setLastName(obj.getString("lastName"));
                if (obj.has("identification"))
                    customer.setIdentification(obj.optString("identification"));
                if (obj.has("passportNumber"))
                    customer.setPassportNumber(obj.optString("passportNumber"));
                if (obj.has("tel"))
                    customer.setTel(obj.getString("tel"));
                if (obj.has("address"))
                    customer.setAddress(obj.getString("address"));
                if (obj.has("birthday"))
                    customer.setBirthday(DatetimeConverter.parseLocalDate(obj.getString("birthday"), "yyyy-MM-dd"));
                if (obj.has("gender"))
                    customer.setGender(obj.getString("gender"));
                if (obj.has("country"))
                    customer.setCountry(obj.getString("country"));
                if (obj.has("email"))
                    customer.setEmail(obj.getString("email"));
                if (obj.has("remark"))
                    customer.setRemark(obj.optString("remark"));
                if (obj.has("titleOfCourtesy"))
                    customer.setTitleOfCourtesy(obj.optString("titleOfCourtesy"));
                if (obj.has("allowPromotionMail"))
                    customer.setAllowPromotionMail(obj.optString("allowPromotionMail"));
                if (obj.has("username"))
                    customer.setUsername(obj.getString("username"));
                if (obj.has("password")) {
                    String encodedPassword = passwordEncoder.encode(obj.getString("password"));
                    customer.setPassword(encodedPassword);
                }

                if (obj.has("memberStatus"))
                    customer.setMemberStatus(obj.getString("memberStatus"));
                if (obj.has("totalPoints"))
                    customer.setTotalPoints(obj.getInt("totalPoints"));

                customerloginServiceCHJ.save(customer);
                return new ResponseEntity<>(new JSONObject().put("status", "success").toString(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new JSONObject().put("status", "error").put("message", "Customer not found").toString(),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new JSONObject().put("status", "error").put("message", e.getMessage()).toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}