package nashtech.training.ordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import nashtech.training.ordersystem.dto.request.returnrequest.UpdateReturnRequestDTO;
import nashtech.training.ordersystem.dto.response.returnrequest.ReturnRequestItemResponseDTO;
import nashtech.training.ordersystem.dto.request.returnrequest.ReturnRequestDTO;
import nashtech.training.ordersystem.dto.response.returnrequest.ReturnRequestResponseDTO;
import nashtech.training.ordersystem.entity.*;
import nashtech.training.ordersystem.repository.*;
import nashtech.training.ordersystem.service.ReturnRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReturnRequestServiceImpl implements ReturnRequestService {

    private final ReturnRequestsRepository returnRequestsRepo;
    private final ReturnRequestItemsRepository returnRequestItemsRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final UserRepository userRepo;

    @Override
    public ReturnRequestResponseDTO createReturnRequest(Long userId, ReturnRequestDTO dto) {
        // Fetch order
        Order order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Fetch user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create and save return request
        ReturnRequests request = ReturnRequests.builder()
                .order(order)
                .user(user)
                .reasonCode(dto.getReasonCode())
                .customerComment(dto.getCustomerComment())
                .status(Status.PENDING_APPROVAL)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        returnRequestsRepo.save(request);

        // Create and save return request items
        List<ReturnRequestItems> savedItems = dto.getItems().stream().map(itemDTO -> {
            OrderItem orderItem = orderItemRepo.findById(itemDTO.getOrderItemId())
                    .orElseThrow(() -> new RuntimeException("Order item not found"));

            return ReturnRequestItems.builder()
                    .returnRequest(request)
                    .orderItem(orderItem)
                    .quantity(itemDTO.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        returnRequestItemsRepo.saveAll(savedItems);

        // Convert saved items to response DTOs
        List<ReturnRequestItemResponseDTO> itemResponseDTOs = savedItems.stream()
                .map(item -> new ReturnRequestItemResponseDTO(
                        item.getOrderItem().getId(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        // Return the response DTO
        return ReturnRequestResponseDTO.builder()
                .id(request.getId())
                .orderId(order.getId())
                .userId(user.getId())
                .reasonCode(request.getReasonCode())
                .customerComment(request.getCustomerComment())
                .status(request.getStatus())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .items(itemResponseDTOs)
                .build();
    }

    @Override
    public List<ReturnRequestResponseDTO> getUserReturnRequests(Long userId) {
        return returnRequestsRepo.findByUserId(userId)
                .stream()
                .map(request -> {
                    List<ReturnRequestItems> items = returnRequestItemsRepo.findByReturnRequestId(request.getId());
                    return ReturnRequestResponseDTO.builder()
                            .id(request.getId())
                            .orderId(request.getOrder().getId())
                            .userId(userId)
                            .reasonCode(request.getReasonCode())
                            .customerComment(request.getCustomerComment())
                            .status(request.getStatus())
                            .createdAt(request.getCreatedAt())
                            .updatedAt(request.getUpdatedAt())
                            .items(items.stream().map(item -> ReturnRequestItemResponseDTO.builder()
                                    .orderItemId(item.getOrderItem().getId())
                                    .quantity(item.getQuantity())
                                    .build()).collect(Collectors.toList()))
                            .build();
                }).collect(Collectors.toList());
    }
    @Override
    public ReturnRequestResponseDTO getById(Long id) {
        ReturnRequests entity = returnRequestsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Return request not found"));
        return toDTO(entity);
    }
    @Override
    public List<ReturnRequestResponseDTO> getAll() {
        return returnRequestsRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    private ReturnRequestResponseDTO toDTO(ReturnRequests request) {
        return ReturnRequestResponseDTO.builder()
                .id(request.getId())
                .orderId(request.getOrder().getId())
                .userId(request.getUser().getId())
                .reasonCode(request.getReasonCode())
                .customerComment(request.getCustomerComment())
                .adminComment(request.getAdminComment())
                .status(request.getStatus())
                .resolutionType(request.getResolutionType())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .items(
                        returnRequestItemsRepo.findByReturnRequestId(request.getId()).stream()
                                .map(item -> ReturnRequestItemResponseDTO.builder()
                                        .orderItemId(item.getOrderItem().getId())
                                        .quantity(item.getQuantity())
                                        .build()
                                ).collect(Collectors.toList())
                )
                .build();
    }
    @Override
    public ResponseEntity<Void> deleteReturnRequest(Long id) {
        ReturnRequests request = returnRequestsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Return request not found"));

        if (request.isDeleted()) {
            throw new RuntimeException("Return request is already deleted."); // Already deleted
        }

        request.setDeleted(true);
        request.setDeletedAt(LocalDateTime.now());

        returnRequestsRepo.save(request); // persist soft-delete changes
        return ResponseEntity.noContent().build();
    }

    @Override
    public ReturnRequestResponseDTO updateReturnRequest(Long id, UpdateReturnRequestDTO dto) {
        ReturnRequests existing = returnRequestsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Return request not found"));

        // Apply updates
        existing.setReasonCode(dto.getReasonCode());
        existing.setCustomerComment(dto.getAdminComment());
        existing.setResolutionType(dto.getResolutionType());
        existing.setStatus(dto.getStatus());

        // Persist updated entity
        ReturnRequests saved = returnRequestsRepo.save(existing);

        // Return manually constructed DTO for consistency
        return toDTO(saved);
    }
}

