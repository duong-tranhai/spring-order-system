package nashtech.training.ordersystem.dto.response.returnrequest;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnRequestItemResponseDTO {
    private Long orderItemId;
    private Integer quantity;
}
