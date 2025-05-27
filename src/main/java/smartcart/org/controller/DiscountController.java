package smartcart.org.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.DiscountDto;
import smartcart.org.service.DiscountService;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<DiscountDto> createDiscount(@RequestBody DiscountDto dto) {
        return ResponseEntity.status(201).body(discountService.createDiscount(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(discountService.getDiscount(id));
    }

    @GetMapping
    public ResponseEntity<List<DiscountDto>> getAllDiscounts() {
        return ResponseEntity.ok(discountService.getDiscounts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountDto> updateDiscount(@PathVariable("id") Long id, @RequestBody DiscountDto dto) {
        return ResponseEntity.ok(discountService.updateDiscount(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiscount(@PathVariable("id") Long id) {
        if (Boolean.TRUE.equals(discountService.deleteDiscount(id))) {
            return ResponseEntity.status(201).body("Discount deleted successfully");
        }
        return ResponseEntity.status(404).body("Discount not found");
    }
}
