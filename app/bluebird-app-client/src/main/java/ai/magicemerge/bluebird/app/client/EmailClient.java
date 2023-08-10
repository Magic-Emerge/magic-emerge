package ai.magicemerge.bluebird.app.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Component
public class EmailClient {
    private static JavaMailSenderImpl staticjavaMailSender;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @PostConstruct
    public void init() {
        staticjavaMailSender = javaMailSender;
    }


    public static void sendSimpleEmail(String from, String address, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setFrom(from);
        mailMessage.setTo(address);
        staticjavaMailSender.send(mailMessage);
    }

    public static void sendHtmlMail(String from,  String to, String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = staticjavaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            message.setSubject(subject);


            messageHelper.setText(content, true);
            staticjavaMailSender.send(message);
            log.info("邮箱验证码发送成功");
        } catch (MessagingException e) {
            log.info("邮箱验证码发送失败", e);
        }
    }

}

