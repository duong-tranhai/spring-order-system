package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.entity.Discount;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.entity.User;
import nashtech.training.ordersystem.repository.DiscountRepository;
import nashtech.training.ordersystem.repository.ProductRepository;
import nashtech.training.ordersystem.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {

    private final DiscountRepository discountRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public DiscountService(DiscountRepository discountRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.discountRepo = discountRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public Discount applyDiscountToProduct(String sellerUsername, Long productId, Discount discount) {
        User seller = userRepo.findByUsername(sellerUsername)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSupplier().getClass().equals(sellerUsername)) {
            throw new AccessDeniedException("Unauthorized to modify this product.");
        }

        discount.setProduct(product);
        discount.setSeller(seller);
        return discountRepo.save(discount);
    }

    public List<Discount> getDiscountsForSeller(String sellerUsername) {
        return discountRepo.findBySellerUsername(sellerUsername);
    }
}
