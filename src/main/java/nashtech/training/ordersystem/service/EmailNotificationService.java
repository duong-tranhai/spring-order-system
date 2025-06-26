package nashtech.training.ordersystem.service;

import java.util.Map;

public interface EmailNotificationService {
    void sendOrderStatusEmail(String to, String subject, Map<String, Object> variables);
}
