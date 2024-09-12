package com.housing.back.provider;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailProvider {

    private final JavaMailSender javaMailSender;
    String SUBJECT = "[OAUTH TEST]";

    // certificationNumber -> 인증번호
    public boolean sendCertificationMail(String email, String certificationNumber) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            String htmlContent = getCertificationMessage(certificationNumber);

            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);
            messageHelper.setFrom("goodlytype@gmail.com"); // **********

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String getCertificationMessage(String certificationNumber) {
        String certificationMessage = "";
        certificationMessage += "<h1 style='text-align: center;'>OAuth 인증메일</h1>";
        certificationMessage += "<h3 style='text-align: center;'>OAuth <strong style='font-size: 32px; letter-spacing: 8px;'>"
                + certificationNumber + "</strong></h3>";
        return certificationMessage;
    }

}
