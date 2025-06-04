package nashtech.training.ordersystem.dto;

public record ReportResponseDTO(
        long totalUsers,
        long totalOrders,
        double totalSales,
        long totalProducts
) {}
