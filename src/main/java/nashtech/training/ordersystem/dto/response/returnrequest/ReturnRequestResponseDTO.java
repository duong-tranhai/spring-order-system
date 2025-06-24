package nashtech.training.ordersystem.dto.response.returnrequest;

import lombok.*;
//import nashtech.training.ordersystem.entity.ReasonCode;
import nashtech.training.ordersystem.entity.Status;
import nashtech.training.ordersystem.entity.ResolutionType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnRequestResponseDTO {
    private Long id;
    private Long orderId;
    private Long userId;
    private String reasonCode;
    private String customerComment;
    private String adminComment;
    private Status status;
    private ResolutionType resolutionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ReturnRequestItemResponseDTO> items;
}
