package smartcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcart.org.entity.InventoryLog;

public interface InventoryLogRepository extends JpaRepository<InventoryLog, Long> {
}
