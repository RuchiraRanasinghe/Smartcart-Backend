package smartcart.org.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.SaleDto;
import smartcart.org.response.ApiResponse;
import smartcart.org.service.SaleService;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<ApiResponse<SaleDto>> persist(@NonNull @Valid @RequestBody SaleDto saleDto) {
        SaleDto createdSale = saleService.createSale(saleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,HttpStatus.CREATED.value(), createdSale));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleDto>> get(@NonNull @PathVariable("id") Long id) {
        SaleDto sale = saleService.findSale(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), sale));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SaleDto>>> getAll() {
        List<SaleDto> sales = saleService.findAllSales();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), sales));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleDto>> update(@NonNull @PathVariable("id") Long id, @NonNull @Valid @RequestBody SaleDto saleDto) {
        SaleDto updatedSale = saleService.updateSale(id, saleDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), updatedSale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@NonNull @PathVariable("id") Long id) {
        if (Boolean.TRUE.equals(saleService.deleteSale(id))) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,HttpStatus.CREATED.value(), null,"Sale deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(true,HttpStatus.NOT_FOUND.value(), null,"Sale Not successfully"));
    }
}