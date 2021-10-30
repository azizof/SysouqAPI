package com.sysouq.sysouqapi.services;


import com.sysouq.sysouqapi.entities.user.User;
import com.sysouq.sysouqapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

@Service
public class EmailService {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Autowired
    public EmailService(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }


    /**
     * the java mail sender with configurations
     *
     * @return the java mail sender with configurations
     */
    @Bean
    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("=============");
        mailSender.setPassword("=============");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    /**
     * send password token to user email after user on client side clicked rest password
     *
     * @param email the email address of the user which want to reset the password
     */
    public EmailSendHelper sendPasswordToken(String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            String tokenString = String.format("%06d", new Random().nextInt(999999));
            tokenService.addToken(tokenString, email);

            MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");
            try {
                message.setFrom("=============@gmail.com");
                message.setTo(email);
                message.setSubject("إعادة تعيين كلمة السر الخاص بك Sysouq");
                String msg = "<html dir=\"rtl\" lang=\"ar\">" +
                        "<body>" +
                        "<h3> " + " مرحباَ " + "</h3><br/>" +
                        "<p> لقد قمت بطلب اعادة تعيين كلمة المرور <p>" + "<br/>" +
                        " يرجى استخدام الارقام التالية لتعملية التأكيد" + "<br/><br/>" +
                        "<b> " + tokenString + "</b>" + "<br/><br/>" +
                        "لديك فقة 15 دقيقة لتغيير كلمة السر والا يجب اعادة طلب كلمة السر مرة اخرى"
                ;
                message.setText(msg, true);
                getJavaMailSender().send(mimeMessage);
                return new EmailSendHelper("success", "email sent successfully");
            } catch (MessagingException ex) {
                ex.printStackTrace();
                return new EmailSendHelper("error", ex.getMessage());
            }
        } else {
            return new EmailSendHelper("error", "user not exist");
        }


    }

    /**
     * chek if token is valid
     *
     * @param token the token as string
     * @param email the email address
     * @return true if token is valid
     */
    public EmailSendHelper checkEmailToken(String token, String email) {
        if (tokenService.isTokenValid(token, email))
            return new EmailSendHelper("success", "token valid");
        else
            return new EmailSendHelper("error", "token not valid");
    }

    /**
     * send welcome text to user email
     *
     * @param user the user
     */
    public void sendCreateEmailConfirm(User user) {
        MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");
        try {
            message.setFrom("=============@gmail.com");
            message.setTo(user.getEmail());
            message.setSubject("تم انشاء الحساب على منصة Sysouq");
            String msg = "<html dir=\"rtl\" lang=\"ar\">" +
                    "<body>" +
                    "<h3> " + user.getName() + " مرحباَ " + "</h3><br/>" +
                    "<p> مبروك <p>" + "<br/>" +
                    "لقد تم انشاء حسابك بنجاح على منصة Sysouq" + "<br/><br/>" +
                    "<b> </b>" + "<br/><br/>" +
                    "نتمنى ان تسركم خدمتنا"
            ;
            message.setText(msg, true);
            getJavaMailSender().send(mimeMessage);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * send reported item to admin
     *
     * @param itemId the if of the item
     * @param userId the id of the reported user
     */
    public void sendReportEmail(Long itemId, Long userId) {
        MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");
        try {
            message.setFrom("=============@gmail.com");
            message.setTo("=============@hotmail.com");
            message.setSubject("بلاغ من sysouq");
            String msg = "<html >" +
                    "<body> " +
                    " user with the id " + userId +
                    " reported item with the id " + itemId
            ;
            message.setText(msg, true);
            getJavaMailSender().send(mimeMessage);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public static class EmailSendHelper {
        private String status;
        private String message;

        public EmailSendHelper(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
