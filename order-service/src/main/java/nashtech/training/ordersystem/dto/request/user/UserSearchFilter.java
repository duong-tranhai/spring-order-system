package nashtech.training.ordersystem.dto.request.user;

public record UserSearchFilter(String column, String value, boolean isAsc) {
}
