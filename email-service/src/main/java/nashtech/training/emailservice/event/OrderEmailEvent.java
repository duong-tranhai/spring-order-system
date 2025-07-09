package nashtech.training.emailservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEmailEvent {
    private String to;
    private String subject;
    private String orderId;
    private String status;
}

