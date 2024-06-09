package com.ispan.hotel.controller.member;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.service.member.LoginServiceCHJ;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class LoginControllerCHJ {
    @Autowired
    private LoginServiceCHJ loginServiceCHJ;

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpSession session, @RequestBody String loginRequest) {
        JSONObject request = new JSONObject(loginRequest);
        String username = request.getString("username");
        String password = request.getString("password");
        boolean isValid = loginServiceCHJ.validateLogin(username, password);

        if (isValid) {
            // session.setAttribute("username", username);
            return ResponseEntity.ok("{\"message\":\"登入成功\"}");

        } else {
            return ResponseEntity.ok("{\"message\":\"登入失敗或帳號信箱未驗證\"}");
        }
    }
}
