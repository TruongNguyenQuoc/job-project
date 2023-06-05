package com.project.tutoronline.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tutoronline.model.dto.EmailAccountDTO;
import com.project.tutoronline.model.dto.EmailTemplateDTO;
import com.project.tutoronline.model.dto.MailDTO;
import com.project.tutoronline.service.EmailService;
import com.project.tutoronline.utils.ConstantUtil;
import com.project.tutoronline.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String mailFrom;

    private Map<String, Object> properties =  new HashMap<>();

    @Override
    public boolean sendEmailForForgotPassword(EmailAccountDTO emailDTO) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            //enCoder base64
            Date date = new Date();
            MailDTO mailDTO = new MailDTO();
            mailDTO.setTime(DateUtil.convertDateToString(date, "HH:mm:ss dd-MM-yyyy"));
            mailDTO.setData(emailDTO);
            mailDTO.setMinutes(30);
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writeValueAsString(mailDTO);
            String linkActive = ConstantUtil.HOST_URL + "/forgot-password/confirm?ref=" + encoderStringToBase64(result);

            properties.put("fullname", emailDTO.getFullName());
            properties.put("email", emailDTO.getEmail());
            properties.put("password", emailDTO.getPassword());
            properties.put("link", linkActive);
            properties.put("emailContact", "Email: tutoronline@gmail.com");
            properties.put("phoneContact", "Hotline: 0123-456-789");

            Context context = new Context();
            context.setVariables(properties);

            String html = templateEngine.process("mail/forgot_password.html", context);

            helper.setFrom(mailFrom);
            helper.setTo(emailDTO.getEmail());
            helper.setSubject("Yêu Cầu Cấp Lại Mật Khẩu Tại www.tutoronline.vn");
            helper.setText(html, true);

            javaMailSender.send(message);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendEmailForCheckout(EmailTemplateDTO emailTemplateDTO) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();

            // set properties
            if (emailTemplateDTO.getProperties() != null && !emailTemplateDTO.getProperties().isEmpty()) {
                context.setVariables(emailTemplateDTO.getProperties());
            }

            String html = templateEngine.process("mail/checkout.html", context);

            helper.setFrom(mailFrom);
            helper.setTo(emailTemplateDTO.getTo());
            if (emailTemplateDTO.getCc() != null) {
                helper.setCc(emailTemplateDTO.getCc());
            }
            helper.setSubject(emailTemplateDTO.getSubject());
            helper.setText(html, true);

            javaMailSender.send(message);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendEmailForRegister(EmailTemplateDTO emailTemplateDTO) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();

            // set properties
            if (emailTemplateDTO.getProperties() != null && !emailTemplateDTO.getProperties().isEmpty()) {
                context.setVariables(emailTemplateDTO.getProperties());
            }

            String html = "";

            html = templateEngine.process("mail/welcome_tutor.html", context);

            helper.setFrom(mailFrom);
            helper.setTo(emailTemplateDTO.getTo());
            helper.setSubject(emailTemplateDTO.getSubject());
            helper.setText(html, true);

            javaMailSender.send(message);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String encoderStringToBase64(String text) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(text.getBytes(StandardCharsets.UTF_8) );
    }

    public static String decoderBase64ToString(String text) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(text);
        return new String(decodedByteArray);
    }

}
