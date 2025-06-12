package nashtech.training.ordersystem.controller;

import nashtech.training.ordersystem.entity.ReturnRequest;
import nashtech.training.ordersystem.service.ReturnRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
public class ReturnRequestController {

    private final ReturnRequestService returnService;

    public ReturnRequestController(ReturnRequestService returnService) {
        this.returnService = returnService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ReturnRequest> submitReturn(@RequestParam Long orderId,
                                                      @RequestParam Long productId,
                                                      @RequestParam String reason,
                                                      Authentication auth) {
        ReturnRequest req = returnService.requestReturn(auth.getName(), orderId, productId, reason);
        return ResponseEntity.ok(req);
    }

    @GetMapping("/my-requests")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<ReturnRequest> getCustomerReturns(Authentication auth) {
        return returnService.getRequestsForCustomer(auth.getName());
    }

    @GetMapping("/seller-requests")
    @PreAuthorize("hasRole('SELLER')")
    public List<ReturnRequest> getSellerReturns(Authentication auth) {
        return returnService.getRequestsForSeller(auth.getName());
    }

    @PutMapping("/{id}/respond")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ReturnRequest> respondToReturn(@PathVariable Long id,
                                                         @RequestParam boolean approve,
                                                         @RequestParam String response) {
        ReturnRequest updated = returnService.respondToRequest(id, response, approve);
        return ResponseEntity.ok(updated);
    }
}
