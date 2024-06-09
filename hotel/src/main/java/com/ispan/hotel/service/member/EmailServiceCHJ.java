package com.ispan.hotel.service.member;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceCHJ {

    @Autowired
    private JavaMailSender emailSender;

    public void sendHtmlEmail(String to, String senderName, String subject, String htmlContent) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // true 表示支持 HTML

            helper.setTo(to);
            helper.setSubject(subject);
            // 寫上自己的gmail
            helper.setFrom(new InternetAddress("@gmail.com", senderName, "UTF-8"));
            helper.setText(htmlContent, true); // HTML 格式

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void sendVerificationCode(String to, String verificationCode) {
        String subject = "Email帳號驗證";
        String content = "驗證碼: " + verificationCode;
        // 使用通用
        sendHtmlEmail(to, "密碼重置🔒", subject, content);
    }
}