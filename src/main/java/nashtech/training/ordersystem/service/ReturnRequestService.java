package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.entity.ReturnRequest;

import java.util.List;

public interface ReturnRequestService {
    ReturnRequest requestReturn(String customerUsername, Long orderId, Long productId, String reason);
    List<ReturnRequest> getRequestsForCustomer(String username);
    List<ReturnRequest> getRequestsForSeller(String sellerUsername);
    ReturnRequest respondToRequest(Long requestId, String response, boolean approve);
}