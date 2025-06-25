package nashtech.training.ordersystem.dto.request.returnrequest;

import lombok.*;
import nashtech.training.ordersystem.dto.response.returnrequest.ReturnRequestItemResponseDTO;
import nashtech.training.ordersystem.entity.ResolutionType;
import nashtech.training.ordersystem.entity.Status;
//import nashtech.training.ordersystem.entity.ReasonCode;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnRequestDTO {
    Long userId;
    private Long orderId;
    private String reasonCode;
    private String customerComment;
    private List<ReturnRequestItemRequestDTO> items;
}
