package nashtech.training.emailservice.controller;

import nashtech.training.emailservice.dto.EmailEvent;
import nashtech.training.emailservice.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailServiceImpl emailService;

    @PostMapping
    public void sendEmail(@RequestBody EmailEvent email) {
        emailService.sendEmail(email);
    }
}
