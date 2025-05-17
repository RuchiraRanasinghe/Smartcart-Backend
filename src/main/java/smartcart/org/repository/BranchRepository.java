package smartcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcart.org.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
