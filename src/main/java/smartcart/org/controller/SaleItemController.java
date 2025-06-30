package smartcart.org.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.SaleItemDto;
import smartcart.org.response.ApiResponse;
import smartcart.org.service.SaleItemService;

import java.util.List;

@RestController
@RequestMapping("/api/sale-item")
@RequiredArgsConstructor
public class SaleItemController {

    private final SaleItemService saleItemService;

    @PostMapping
    public ResponseEntity<ApiResponse<SaleItemDto>> createSaleItem(@NonNull @Valid @RequestBody SaleItemDto saleItemDto) {
        SaleItemDto createdSaleItem = saleItemService.createSaleItem(saleItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, HttpStatus.CREATED.value(), createdSaleItem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleItemDto>> getSaleItemById(@NonNull @PathVariable("id") Long id) {
        SaleItemDto saleItem = saleItemService.findSaleItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, HttpStatus.OK.value(), saleItem));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SaleItemDto>>> getAllSaleItems() {
        List<SaleItemDto> saleItems = saleItemService.findAllSaleItems();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, HttpStatus.OK.value(), saleItems));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleItemDto>> updateSaleItem(@NonNull @PathVariable("id") Long id, @NonNull @Valid @RequestBody SaleItemDto saleItemDto) {
        SaleItemDto updatedSaleItem = saleItemService.updateSaleItem(id, saleItemDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, HttpStatus.OK.value(), updatedSaleItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteSaleItemById(@NonNull @PathVariable("id") Long id) {
        if (Boolean.TRUE.equals(saleItemService.deleteSaleItemById(id))){
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,HttpStatus.CREATED.value(), null,"Sale Item deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(true,HttpStatus.NOT_FOUND.value(), null,"Sale Item Not Found"));
    }
}