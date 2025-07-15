package nashtech.training.emailservice.service;

import nashtech.training.emailservice.dto.EmailEvent;

public interface EmailService {
    void sendEmail(EmailEvent event);
}
