package nashtech.training.ordersystem.service.impl;

import nashtech.training.ordersystem.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class EmailNotificationServiceImpl implements EmailNotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Sends a simple text-based email.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param text    The body of the email.
     */
    @Async
    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // You can set the "from" address if configured, otherwise it will use the
            // one from application.properties
            // message.setFrom("noreply@yourdomain.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            javaMailSender.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error while sending email: " + e.getMessage());
            // Consider a more robust error handling strategy
        }
    }
}
