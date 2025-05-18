package smartcart.org.controller;

import lombok.RequiredArgsConstructor;
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
    public SaleItemDto createSaleItem(@RequestBody SaleItemDto saleItemDto) {
        return saleItemService.createSaleItem(saleItemDto);
    }

    @GetMapping("/{id}")
    public SaleItemDto getSaleItemById(@PathVariable("id") Long id) {
        return saleItemService.findSaleItemById(id);
    }

    @GetMapping
    public List<SaleItemDto> getAllSaleItems() {
        return saleItemService.findAllSaleItems();
    }

    @PutMapping("/{id}")
    public SaleItemDto updateSaleItem(@PathVariable("id") Long id, @RequestBody SaleItemDto saleItemDto) {
        return saleItemService.updateSaleItem(id, saleItemDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSaleItemById(@PathVariable("id") Long id) {
        return saleItemService.deleteSaleItemById(id);
    }
}
