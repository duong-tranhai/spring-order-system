package nashtech.training.ordersystem.controller;

import nashtech.training.ordersystem.entity.Discount;
import nashtech.training.ordersystem.service.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/product/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Discount> createDiscount(@PathVariable Long productId,
                                                   @RequestBody Discount discount,
                                                   Authentication auth) {
        String sellerUsername = auth.getName();
        Discount saved = discountService.applyDiscountToProduct(sellerUsername, productId, discount);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    @PreAuthorize("hasRole('SELLER')")
    public List<Discount> listDiscounts(Authentication auth) {
        return discountService.getDiscountsForSeller(auth.getName());
    }
}
