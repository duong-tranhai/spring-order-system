package nashtech.training.emailservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailEvent {
    private String to;
    private String subject;
    private String body;
}
