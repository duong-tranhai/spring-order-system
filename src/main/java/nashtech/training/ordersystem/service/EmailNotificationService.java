package nashtech.training.ordersystem.service;

public interface EmailNotificationService {
    void sendEmail(String to, String subject, String text);
}