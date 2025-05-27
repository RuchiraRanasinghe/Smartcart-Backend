package smartcart.org.service;

import smartcart.org.dto.SupplierDto;
import java.util.List;

public interface SupplierService {

    SupplierDto createSupplier(SupplierDto supplierDto);

    SupplierDto getSupplierById(Long id);

    List<SupplierDto> getAllSuppliers();

    SupplierDto updateSupplier(Long id, SupplierDto supplierDto);

    Boolean deleteSupplier(Long id);
}
