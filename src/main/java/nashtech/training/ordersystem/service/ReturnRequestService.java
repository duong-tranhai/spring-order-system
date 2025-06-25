package nashtech.training.ordersystem.service;


import nashtech.training.ordersystem.dto.request.returnrequest.ReturnRequestDTO;
import nashtech.training.ordersystem.dto.request.returnrequest.UpdateReturnRequestDTO;
import nashtech.training.ordersystem.dto.response.returnrequest.ReturnRequestResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReturnRequestService {
    ReturnRequestResponseDTO createReturnRequest(Long userId, ReturnRequestDTO dto);
    List<ReturnRequestResponseDTO> getUserReturnRequests(Long userId);
    ReturnRequestResponseDTO getById(Long id);
    List<ReturnRequestResponseDTO> getAll();
    ReturnRequestResponseDTO updateReturnRequest(Long id, UpdateReturnRequestDTO dto);
    ResponseEntity<Void> deleteReturnRequest(Long id);
}
