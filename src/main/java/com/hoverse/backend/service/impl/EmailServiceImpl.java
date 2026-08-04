package com.hoverse.backend.service.impl;

import com.hoverse.backend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 03/08/2026
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    @Async("emailTaskExecutor")
    public void sendVerificationEmail(String toEmail, String token) {
        try {
            String verificationLink = "http://localhost:5173/verify-email?token="+token;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom(fromEmail, "Hoverse Support");
            helper.setTo(toEmail);
            helper.setSubject("Xác thực tài khoản Hoverse");

            String htmlContent = "<h2>Chào mừng bạn đến với Hoverse!</h2>"
                    + "<p>Vui lòng click vào đường link bên dưới để xác thực tài khoản của bạn. Link này có hiệu lực trong 15 phút:</p>"
                    + "<a href=\"" + verificationLink + "\" style=\"display: inline-block; padding: 10px 20px; color: white; background-color: #007bff; text-decoration: none; border-radius: 5px;\">Xác thực Email</a>"
                    + "<p>Hoặc copy link này dán vào trình duyệt: " + verificationLink + "</p>"
                    + "<p>Trân trọng,<br>Đội ngũ Hoverse</p>";

            helper.setText(htmlContent,true);

            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("Có lỗi khi gửi email tới "+toEmail+": "+e.getMessage() );
        }
    }
}
