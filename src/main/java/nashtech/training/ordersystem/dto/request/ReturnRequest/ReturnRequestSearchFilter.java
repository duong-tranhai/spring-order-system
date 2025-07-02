package nashtech.training.ordersystem.dto.request.ReturnRequest;

public record ReturnRequestSearchFilter(String column, String value, boolean isAsc) {
}