package smartcart.org.service;

import smartcart.org.dto.SaleItemDto;

import java.util.List;

public interface SaleItemService {

    SaleItemDto createSaleItem(SaleItemDto saleItemDto);

    SaleItemDto findSaleItemById(Long id);

    List<SaleItemDto> findAllSaleItems();

    SaleItemDto updateSaleItem(Long id, SaleItemDto saleItemDto);

    Boolean deleteSaleItemById(Long id);
}
