package smartcart.org.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.InventoryLogDto;
import smartcart.org.service.InventoryLogService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-log")
@RequiredArgsConstructor
@Slf4j
public class InventoryLogController {

    private final InventoryLogService inventoryLogService;

    @PostMapping
    public ResponseEntity<InventoryLogDto> create(@RequestBody InventoryLogDto logDto) {
        return ResponseEntity.status(201).body(inventoryLogService.createLog(logDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryLogDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(inventoryLogService.getLogById(id));
    }

    @GetMapping
    public ResponseEntity<List<InventoryLogDto>> getAll() {
        return ResponseEntity.ok(inventoryLogService.getAllLogs());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryLogDto> update(@PathVariable("id") Long id, @RequestBody InventoryLogDto logDto) {
        return ResponseEntity.ok(inventoryLogService.updateLog(id, logDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        if (Boolean.TRUE.equals(inventoryLogService.deleteLog(id))) {
            return ResponseEntity.status(201).body("Inventory log deleted successfully");
        }
        return ResponseEntity.status(400).body("Inventory log not found.");
    }
}
