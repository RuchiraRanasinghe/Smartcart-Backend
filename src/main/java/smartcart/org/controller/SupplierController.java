package smartcart.org.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.SupplierDto;
import smartcart.org.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<SupplierDto>>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), supplierService.getAllSuppliers()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierDto>> update(@PathVariable("id") Long id, @RequestBody SupplierDto dto) {
        if (dto.getId() != null && !dto.getId().equals(id)) {
            throw new IllegalArgumentException("Path variable ID and JSON ID must match");
        }
        dto.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(),supplierService.updateSupplier(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        if (Boolean.TRUE.equals(supplierService.deleteSupplier(id))){
            return ResponseEntity.status(201).body("Supplier deleted successfully.");
        }
        return ResponseEntity.status(404).body("Supplier not found.");
    }
}
