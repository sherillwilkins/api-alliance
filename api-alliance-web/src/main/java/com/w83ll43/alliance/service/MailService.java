package com.w83ll43.alliance.service;

import com.w83ll43.alliance.model.enums.EmailTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String SENDER;

    private static final String WEBSITE_NAME = "API联盟";

    /**
     * 发送验证码邮件
     * @param type  邮件类型
     * @param email 收件人邮箱
     * @param code  验证码
     */
    public void sendMail(Integer type, String email, String code) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);
        // 发件人邮箱和名称
        helper.setFrom(SENDER, WEBSITE_NAME);
        // 收件人邮箱
        helper.setTo(email);
        // 邮件标题
        helper.setSubject(WEBSITE_NAME + "邮箱验证码");
        // 邮件正文 第二个参数表示是否是 HTML 正文

        Context context = new Context();
        context.setVariable("name", WEBSITE_NAME);
        context.setVariable("type", EmailTypeEnum.of(type).getDesc());
        context.setVariable("code", Arrays.asList(code.split("")));
        helper.setText(templateEngine.process("code", context), true);
        javaMailSender.send(message);
    }

    /**
     * 发送注册验证码邮件
     * @param email 收件人邮箱
     * @param code  验证码
     */
    public void sendRegisterCodeEmail(String email, String code) throws MessagingException, UnsupportedEncodingException {
        sendMail(EmailTypeEnum.REGISTER.getType(), email, code);
    }

    /**
     * 发送重置密码验证码邮件
     * @param email 收件人邮箱
     * @param code  验证码
     */
    public void sendResetPasswordCodeEmail(String email, String code) throws MessagingException, UnsupportedEncodingException {
        sendMail(EmailTypeEnum.RESET_PASSWORD.getType(), email, code);
    }

    /**
     * 发送重置邮箱验证码邮件
     * @param email 收件人邮箱
     * @param code  验证码
     */
    public void sendResetEmailCodeEmail(String email, String code) throws MessagingException, UnsupportedEncodingException {
        sendMail(EmailTypeEnum.RESET_EMAIL.getType(), email, code);
    }

    /**
     * 发送普通邮件
     * @param to      接收人
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public void sendSimpleMailMessage(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    /**
     * 发送 HTML 邮件
     * @param to      接收人
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public void sendMimeMessage(String to, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);
        helper.setFrom(SENDER);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }


    /**
     * 发送带附件的邮件
     * @param to      接收人
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public void sendMimeMessage(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(SENDER);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName, file);
        javaMailSender.send(message);
    }

    /**
     * 发送带静态文件的邮件
     * @param to      接收人
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public void sendMimeMessage(String to, String subject, String content, Map<String, String> rscIdMap) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(SENDER);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        for (Map.Entry<String, String> entry : rscIdMap.entrySet()) {
            FileSystemResource file = new FileSystemResource(new File(entry.getValue()));
            helper.addInline(entry.getKey(), file);
        }
        javaMailSender.send(message);
    }
}
