package nashtech.training.ordersystem.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}