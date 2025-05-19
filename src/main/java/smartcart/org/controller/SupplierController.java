package smartcart.org.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.SupplierDto;
import smartcart.org.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
@Slf4j
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierDto> create(@RequestBody SupplierDto dto) {
        return ResponseEntity.status(201).body(supplierService.createSupplier(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @GetMapping
    public ResponseEntity<List<SupplierDto>> getAll() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> update(@PathVariable("id") Long id, @RequestBody SupplierDto dto) {
        if (dto.getId() != null && !dto.getId().equals(id)) {
            throw new IllegalArgumentException("Path variable ID and JSON ID must match");
        }
        dto.setId(id);
        return ResponseEntity.ok(supplierService.updateSupplier(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Supplier deleted successfully.");
    }
}
