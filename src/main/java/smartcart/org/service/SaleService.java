package smartcart.org.service;

import smartcart.org.dto.SaleDto;

import java.util.List;

public interface SaleService {

    SaleDto createSale(SaleDto saleDto);

    SaleDto findSale(Long id);

    List<SaleDto> findAllSales();

    SaleDto updateSale(Long id, SaleDto saleDto);

    boolean deleteSale(Long id);
}
