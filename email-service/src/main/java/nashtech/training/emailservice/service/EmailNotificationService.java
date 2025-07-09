package nashtech.training.emailservice.service;

import java.util.Map;

public interface EmailNotificationService {
    void sendOrderStatusEmail(String to, String subject, Map<String, Object> variables);
}
