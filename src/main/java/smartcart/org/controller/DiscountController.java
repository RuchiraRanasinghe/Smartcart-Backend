package smartcart.org.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.DiscountDto;
import smartcart.org.response.ApiResponse;
import smartcart.org.service.DiscountService;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<ApiResponse<DiscountDto>> createDiscount(@Valid @RequestBody DiscountDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, HttpStatus.CREATED.value(), discountService.createDiscount(dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountDto>> getDiscount(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), discountService.getDiscount(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DiscountDto>>> getAllDiscounts() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), discountService.getDiscounts()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountDto>> updateDiscount(@PathVariable("id") Long id, @Valid @RequestBody DiscountDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), discountService.updateDiscount(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteDiscount(@PathVariable("id") Long id) {
        if (Boolean.TRUE.equals(discountService.deleteDiscount(id))) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,HttpStatus.CREATED.value(), null,"Discount deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(true,HttpStatus.NOT_FOUND.value(), null,"Discount Not Found"));
    }
}
