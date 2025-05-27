package smartcart.org.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.AuditLogDto;
import smartcart.org.service.AuditLogService;

import java.util.List;

@RestController
@RequestMapping("/api/audit-log")
@RequiredArgsConstructor
@Slf4j
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLogDto> persist(@Valid @RequestBody AuditLogDto auditLogDto){
        AuditLogDto persisted = auditLogService.persist(auditLogDto);
        return new ResponseEntity<>(persisted, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditLogDto> update(@PathVariable("id") Long id, @Valid @RequestBody AuditLogDto auditLogDto){
        AuditLogDto updated = auditLogService.update(id, auditLogDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuditLogDto>> findAll(){
        List<AuditLogDto> all = auditLogService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogDto> findById(@PathVariable("id") Long id){
        AuditLogDto auditLogDto = auditLogService.findById(id);
        return new ResponseEntity<>(auditLogDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        if (Boolean.TRUE.equals(auditLogService.deleteById(id))) {
            return ResponseEntity.status(201).body("Deleted successfully");
        }
        return ResponseEntity.status(404).body("Deletion failed");
    }
}
