package smartcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcart.org.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
