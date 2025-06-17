package nashtech.training.ordersystem.controller;

import lombok.RequiredArgsConstructor;
import nashtech.training.ordersystem.dto.request.returnrequest.ReturnRequestDTO;
import nashtech.training.ordersystem.dto.response.returnrequest.ReturnRequestResponseDTO;
import nashtech.training.ordersystem.entity.ResolutionType;
import nashtech.training.ordersystem.service.ReturnRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/returns")
@RequiredArgsConstructor
public class ReturnRequestController {

    private final ReturnRequestService returnRequestService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ReturnRequestResponseDTO> createReturnRequest(@RequestBody ReturnRequestDTO dto) {
        return ResponseEntity.ok(returnRequestService.createReturnRequest(dto.getUserId(),dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SELLER', 'ADMIN')")
    public ResponseEntity<ReturnRequestResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(returnRequestService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SELLER', 'ADMIN')")
    public ResponseEntity<List<ReturnRequestResponseDTO>> getAll() {
        return ResponseEntity.ok(returnRequestService.getAll());
    }

}
