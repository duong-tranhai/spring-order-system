package nashtech.training.ordersystem.dto.request.returnrequest;

import lombok.*;
import nashtech.training.ordersystem.entity.ResolutionType;
import nashtech.training.ordersystem.entity.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReturnRequestDTO {
    private String reasonCode;
    private String adminComment;
    private ResolutionType resolutionType;
    private Status status;
}
