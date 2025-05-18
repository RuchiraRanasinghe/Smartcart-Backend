package smartcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcart.org.entity.SaleItem;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
