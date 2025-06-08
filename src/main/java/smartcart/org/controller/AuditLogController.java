package smartcart.org.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.AuditLogDto;
import smartcart.org.response.ApiResponse;
import smartcart.org.service.AuditLogService;

import java.util.List;

@RestController
@RequestMapping("/api/audit-log")
@RequiredArgsConstructor
@Slf4j
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<ApiResponse<AuditLogDto>> persist(@Valid @RequestBody AuditLogDto auditLogDto){
        AuditLogDto persisted = auditLogService.persist(auditLogDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(),persisted));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AuditLogDto>> update(@PathVariable("id") Long id, @Valid @RequestBody AuditLogDto auditLogDto){
        AuditLogDto updated = auditLogService.update(id, auditLogDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuditLogDto>>> findAll(){
        List<AuditLogDto> all = auditLogService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), all));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuditLogDto>> findById(@PathVariable("id") Long id){
        AuditLogDto auditLogDto = auditLogService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), auditLogDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable("id") Long id){
        if (Boolean.TRUE.equals(auditLogService.deleteById(id))) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), null,"Deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(true,HttpStatus.OK.value(), null,"Deleted Failed"));
    }
}
