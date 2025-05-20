package smartcart.org.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.SaleItemDto;
import smartcart.org.service.SaleItemService;

import java.util.List;

@RestController
@RequestMapping("/api/sale-item")
@RequiredArgsConstructor
public class SaleItemController {

    private final SaleItemService saleItemService;

    @PostMapping
    public ResponseEntity<SaleItemDto> createSaleItem(@NonNull @RequestBody SaleItemDto saleItemDto) {
        SaleItemDto createdSaleItem = saleItemService.createSaleItem(saleItemDto);
        return ResponseEntity.status(201).body(createdSaleItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleItemDto> getSaleItemById(@NonNull @PathVariable("id") Long id) {
        SaleItemDto saleItem = saleItemService.findSaleItemById(id);
        return ResponseEntity.ok(saleItem);
    }

    @GetMapping
    public ResponseEntity<List<SaleItemDto>> getAllSaleItems() {
        List<SaleItemDto> saleItems = saleItemService.findAllSaleItems();
        return ResponseEntity.ok(saleItems);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleItemDto> updateSaleItem(@NonNull @PathVariable("id") Long id, @NonNull @RequestBody SaleItemDto saleItemDto) {
        SaleItemDto updatedSaleItem = saleItemService.updateSaleItem(id, saleItemDto);
        return ResponseEntity.ok(updatedSaleItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSaleItemById(@NonNull @PathVariable("id") Long id) {
        if (saleItemService.deleteSaleItemById(id)){
            return ResponseEntity.status(201).body("Sale item deleted Successfully");
        }
        return ResponseEntity.status(404).body("Sale item not found.");
    }
}