package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.entity.Discount;

import java.util.List;

public interface DiscountService {
    Discount applyDiscountToProduct(String sellerUsername, Long productId, Discount discount);
    List<Discount> getDiscountsForSeller(String sellerUsername);
}