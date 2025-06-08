package smartcart.org.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.InventoryLogDto;
import smartcart.org.response.ApiResponse;
import smartcart.org.service.InventoryLogService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-log")
@RequiredArgsConstructor
@Slf4j
public class InventoryLogController {

    private final InventoryLogService inventoryLogService;

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryLogDto>> create(@Valid @RequestBody InventoryLogDto logDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, HttpStatus.CREATED.value(), inventoryLogService.createLog(logDto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryLogDto>> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), inventoryLogService.getLogById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryLogDto>>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), inventoryLogService.getAllLogs()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryLogDto>> update(@PathVariable("id") Long id, @Valid @RequestBody InventoryLogDto logDto) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), inventoryLogService.updateLog(id, logDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable("id") Long id) {
        if (Boolean.TRUE.equals(inventoryLogService.deleteLog(id))) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,HttpStatus.CREATED.value(), null,"Inventory log deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(true,HttpStatus.NOT_FOUND.value(), null,"Inventory log Not Found"));
    }
}
