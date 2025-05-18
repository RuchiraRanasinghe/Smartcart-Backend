package smartcart.org.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public SaleDto persist(@RequestBody SaleDto saleDto) {
        return saleService.createSale(saleDto);
    }

    @GetMapping("/{id}")
    public SaleDto get(@PathVariable("id") Long id){
        return saleService.findSale(id);
    }

    @GetMapping
    public List<SaleDto> getAll(){
        return saleService.findAllSales();
    }

    @PutMapping("/{id}")
    public SaleDto update(@PathVariable("id") Long id, @Valid @RequestBody SaleDto saleDto){
        return saleService.updateSale(id, saleDto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id){
        return saleService.deleteSale(id);
    }
}
