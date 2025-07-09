package nashtech.training.emailservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import nashtech.training.emailservice.service.EmailNotificationService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    @Async
    @Retryable(
            value = { MessagingException.class, RuntimeException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000, multiplier = 2)
    )
    public void sendOrderStatusEmail(String to, String subject, Map<String, Object> variables) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariables(variables);
            String htmlContent = templateEngine.process("templates.email/order-status.html", context);

            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.err.println("Failed to send HTML email: " + e.getMessage());
        }
    }
}
