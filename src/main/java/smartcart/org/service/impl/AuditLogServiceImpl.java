package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.AuditLogDto;
import smartcart.org.dto.UserDto;
import smartcart.org.entity.AuditLog;
import smartcart.org.entity.User;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.repository.AuditLogRepository;
import smartcart.org.service.AuditLogService;
import smartcart.org.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    String userNotFoundMessage = "User not found with id: ";

    @Override
    public AuditLogDto persist(AuditLogDto auditLog) {
        return saveOrUpdateAuditLog(auditLog);
    }

    @Override
    public AuditLogDto update(Long id, AuditLogDto auditLogDto) {
        if (!auditLogDto.getId().equals(id)) {
            throw new IllegalArgumentException("Audit log ID mismatch.");
        }

        if (!auditLogRepository.existsById(id)) {
            throw new ResourceNotFoundException(userNotFoundMessage + id);
        }

        return saveOrUpdateAuditLog(auditLogDto);
    }

    private AuditLogDto saveOrUpdateAuditLog(AuditLogDto auditLogDto) {
        UserDto userById = userService.findById(auditLogDto.getUserId());
        if (userById == null) {
            throw new ResourceNotFoundException(userNotFoundMessage + auditLogDto.getUserId());
        }

        AuditLog mappedAuditLog = modelMapper.map(auditLogDto, AuditLog.class);
        mappedAuditLog.setUser(modelMapper.map(userById, User.class));
        AuditLog saved = auditLogRepository.save(mappedAuditLog);
        return modelMapper.map(saved, AuditLogDto.class);
    }

    @Override
    public AuditLogDto findById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditLog with ID " + id + " not found."));
        return modelMapper.map(auditLog, AuditLogDto.class);
    }

    @Override
    public List<AuditLogDto> findAll() {
        List<AuditLog> logs = auditLogRepository.findAll();
        return logs.stream()
                .map(log -> modelMapper.map(log, AuditLogDto.class))
                .toList();
    }

    @Override
    public Boolean deleteById(Long id) {
        if (!auditLogRepository.existsById(id)) {
            throw new ResourceNotFoundException("AuditLog with ID " + id + " not found.");
        }
        auditLogRepository.deleteById(id);
        return true;
    }
}
