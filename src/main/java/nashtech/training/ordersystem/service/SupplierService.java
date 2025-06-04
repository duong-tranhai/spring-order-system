package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.entity.Supplier;
import nashtech.training.ordersystem.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Supplier findByName(String name) {
        return supplierRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Supplier not found for username: " + name));
    }
}
