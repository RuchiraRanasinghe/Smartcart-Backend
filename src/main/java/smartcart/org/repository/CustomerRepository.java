package smartcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcart.org.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
