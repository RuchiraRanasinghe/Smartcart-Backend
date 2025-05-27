package smartcart.org.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.SaleDto;
import smartcart.org.service.SaleService;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleDto> persist(@NonNull @Valid @RequestBody SaleDto saleDto) {
        SaleDto createdSale = saleService.createSale(saleDto);
        return ResponseEntity.status(201).body(createdSale);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDto> get(@NonNull @PathVariable("id") Long id) {
        SaleDto sale = saleService.findSale(id);
        return ResponseEntity.ok(sale);
    }

    @GetMapping
    public ResponseEntity<List<SaleDto>> getAll() {
        List<SaleDto> sales = saleService.findAllSales();
        return ResponseEntity.ok(sales);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDto> update(@NonNull @PathVariable("id") Long id, @NonNull @Valid @RequestBody SaleDto saleDto) {
        SaleDto updatedSale = saleService.updateSale(id, saleDto);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@NonNull @PathVariable("id") Long id) {
        if (saleService.deleteSale(id)) {
            return ResponseEntity.status(201).body("Sale deleted Successfully");
        }
        return ResponseEntity.status(404).body("Sale not found.");
    }
}