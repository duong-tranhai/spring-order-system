package nashtech.training.ordersystem.dto.request.returnrequest;

import lombok.*;
import nashtech.training.ordersystem.dto.response.returnrequest.ReturnRequestItemResponseDTO;
//import nashtech.training.ordersystem.entity.ReasonCode;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnRequestItemRequestDTO {
    private Long orderItemId;
    private Integer quantity;
}
