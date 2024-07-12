package uz.guideme.hotelbooking.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.guideme.hotelbooking.service.EmailService;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final static String MAIL_SENDER = "Guide Me";
    private final static String MAIL_SUBJECT = "Your Booking is Confirmed";

    private final JavaMailSender mailSender;

    @Override
    public Optional<Map<String, String>> sendEmail(String email, String text) {
        log.info("Requested to send email");
        mailSender.send(mimeMessage(text, email));

        return Optional.of(Map.of("message", "Successfully sent"));
    }

    private MimeMessage mimeMessage(String mailContent, String email){
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom("dailycodework@gmail.com", MAIL_SENDER);
            messageHelper.setTo(email);
            messageHelper.setSubject(MAIL_SUBJECT);
            messageHelper.setText(mailContent, true);
            return message;
        } catch (UnsupportedEncodingException | MessagingException ex) {
            log.warn(ex.getMessage());
            throw new RuntimeException(ex.getMessage() + ",409");
        }
    }
}
