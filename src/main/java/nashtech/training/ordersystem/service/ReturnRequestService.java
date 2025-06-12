package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.entity.Order;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.entity.ReturnRequest;
import nashtech.training.ordersystem.entity.User;
import nashtech.training.ordersystem.repository.OrderRepository;
import nashtech.training.ordersystem.repository.ProductRepository;
import nashtech.training.ordersystem.repository.ReturnRequestRepository;
import nashtech.training.ordersystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReturnRequestService {

    private final ReturnRequestRepository returnRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public ReturnRequestService(ReturnRequestRepository returnRepo, OrderRepository orderRepo,
                                ProductRepository productRepo, UserRepository userRepo) {
        this.returnRepo = returnRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public ReturnRequest requestReturn(String customerUsername, Long orderId, Long productId, String reason) {
        User customer = userRepo.findByUsername(customerUsername)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if product is in the order & eligible for return
        if (order.getOrderDate().isBefore(LocalTime.from(LocalDate.now().minusDays(30)))) {
            throw new IllegalArgumentException("Return period expired");
        }

        ReturnRequest request = new ReturnRequest();
        request.setCustomer(customer);
        request.setOrder(order);
        request.setProduct(product);
        request.setReason(reason);
        request.setRequestDate(LocalDate.now());
        request.setStatus("PENDING");

        return returnRepo.save(request);
    }

    public List<ReturnRequest> getRequestsForCustomer(String username) {
        return returnRepo.findByCustomerUsername(username);
    }

    public List<ReturnRequest> getRequestsForSeller(String sellerUsername) {
        return returnRepo.findByProduct_SupplierUsername(sellerUsername);
    }

    public ReturnRequest respondToRequest(Long requestId, String response, boolean approve) {
        ReturnRequest req = returnRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        req.setStatus(approve ? "APPROVED" : "REJECTED");
        req.setSellerResponse(response);

        if (approve) {
            Order order = req.getOrder();
            order.setStatus("REFUNDED");
            orderRepo.save(order);
        }

        return returnRepo.save(req);
    }
}
