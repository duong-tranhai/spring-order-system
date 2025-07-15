package nashtech.training.emailservice.service.impl;

import nashtech.training.emailservice.dto.EmailEvent;
import lombok.RequiredArgsConstructor;
import nashtech.training.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${email.from}")
    private String from;

    public void sendEmail(EmailEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(event.getTo());
        message.setSubject(event.getSubject());
        message.setText(event.getBody());

        mailSender.send(message);
    }
}
