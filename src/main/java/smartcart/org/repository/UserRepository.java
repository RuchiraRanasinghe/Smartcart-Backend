package smartcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcart.org.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
