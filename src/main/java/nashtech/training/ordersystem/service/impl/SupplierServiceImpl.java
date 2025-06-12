package nashtech.training.ordersystem.service.impl;

import nashtech.training.ordersystem.entity.Supplier;
import nashtech.training.ordersystem.repository.SupplierRepository;
import nashtech.training.ordersystem.service.SupplierService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier findByName(String name) {
        return supplierRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Supplier not found for username: " + name));
    }
}

