package smartcart.org.service;

import smartcart.org.dto.AuditLogDto;

import java.util.List;

public interface AuditLogService {
    AuditLogDto persist(AuditLogDto auditLog);

    AuditLogDto update(Long id, AuditLogDto auditLogDto);

    AuditLogDto findById(Long id);

    List<AuditLogDto> findAll();

    Boolean deleteById(Long id);
}
