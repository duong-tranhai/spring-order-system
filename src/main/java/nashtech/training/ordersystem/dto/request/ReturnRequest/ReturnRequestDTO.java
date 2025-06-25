package nashtech.training.ordersystem.dto.request.ReturnRequest;

import nashtech.training.ordersystem.entity.Order;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.entity.ReturnRequestStatus;
import nashtech.training.ordersystem.entity.User;

import java.time.LocalDate;

public record ReturnRequestDTO(
        User customer,
        Product product,
        Order order,
        LocalDate requestDate,
        ReturnRequestStatus status,
        String sellerResponse) {}