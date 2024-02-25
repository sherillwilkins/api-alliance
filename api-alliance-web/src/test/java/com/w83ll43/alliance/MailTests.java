package com.w83ll43.alliance;

import com.w83ll43.alliance.common.utils.ValidateCodeUtils;
import com.w83ll43.alliance.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@SpringBootTest
public class MailTests {

    @Autowired
    private MailService mailService;

    @Test
    void testSendSimpleMailMessage() throws MessagingException, UnsupportedEncodingException {
        String email = "2772513085@qq.com";
        mailService.sendRegisterCodeEmail(email, ValidateCodeUtils.generateValidateCodeString(6));
    }
}
