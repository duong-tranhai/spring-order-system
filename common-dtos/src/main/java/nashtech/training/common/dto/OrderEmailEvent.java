package nashtech.training.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEmailEvent {
    private String receiver;
    private String subject;
    private String orderId;
    private String status;
}