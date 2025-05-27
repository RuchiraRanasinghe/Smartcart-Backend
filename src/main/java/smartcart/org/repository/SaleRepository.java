package smartcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcart.org.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
