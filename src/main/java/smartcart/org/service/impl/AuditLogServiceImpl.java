package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.AuditLogDto;
import smartcart.org.entity.AuditLog;
import smartcart.org.repository.AuditLogRepository;
import smartcart.org.service.AuditLogService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final ModelMapper modelMapper;

    @Override
    public AuditLogDto persist(AuditLogDto auditLog) {
        AuditLog saved = auditLogRepository.save(modelMapper.map(auditLog, AuditLog.class));
        return modelMapper.map(saved, AuditLogDto.class);
    }

    @Override
    public AuditLogDto update(Long id, AuditLogDto auditLogDto) {
        return auditLogDto;
    }

    @Override
    public AuditLogDto findById(Long id) {
        return null;
    }

    @Override
    public List<AuditLogDto> findAll() {
        return List.of();
    }

    @Override
    public Boolean deleteById(Long id) {
        return false;
    }
}
