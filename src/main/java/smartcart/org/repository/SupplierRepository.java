package smartcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcart.org.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
