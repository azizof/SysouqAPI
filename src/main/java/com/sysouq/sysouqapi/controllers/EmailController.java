package com.sysouq.sysouqapi.controllers;

import com.sysouq.sysouqapi.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/api/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     * send password token to user email after user on client side clicked rest password
     *
     * @param email the email address of the user which want to reset the password
     */
    @GetMapping("/sendpasswordtoken")
    public EmailService.EmailSendHelper sendPasswordToken(String email) {
       return emailService.sendPasswordToken(email);
    }

    /**
     * chek if token is valid
     *
     * @param token the token as string
     * @param email the email address
     * @return true if token is valid
     */
    @GetMapping("/checktoken")
    public EmailService.EmailSendHelper checkEmailToken(String token, String email) {
        return emailService.checkEmailToken(token, email);
    }


    /**
     * send reported item to admin
     *
     * @param itemId
     * @param userId
     */
    @GetMapping("/report")
    public void sendReportEmail(Long itemId, Long userId) {
        emailService.sendReportEmail(itemId, userId);
    }

}
