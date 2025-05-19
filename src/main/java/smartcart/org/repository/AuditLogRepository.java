package smartcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcart.org.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
