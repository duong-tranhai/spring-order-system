package nashtech.training.ordersystem.dto.request.order;

public record OrderSearchFilter(String column, String value,boolean isAsc) { }
