package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.entity.Supplier;

public interface SupplierService {
    Supplier findByName(String name);
}
