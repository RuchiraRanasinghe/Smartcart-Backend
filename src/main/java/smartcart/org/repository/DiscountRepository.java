package smartcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcart.org.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
