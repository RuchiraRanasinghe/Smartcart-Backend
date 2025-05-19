package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.ProductDto;
import smartcart.org.dto.SaleDto;
import smartcart.org.dto.SaleItemDto;
import smartcart.org.entity.Product;
import smartcart.org.entity.Sale;
import smartcart.org.entity.SaleItem;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.repository.SaleItemRepository;
import smartcart.org.service.ProductService;
import smartcart.org.service.SaleItemService;
import smartcart.org.service.SaleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleItemServiceImpl implements SaleItemService {

    private final SaleItemRepository saleItemRepository;
    private final ModelMapper modelMapper;
    private final SaleService saleService;
    private final ProductService productService;

    String saleItemNotFoundWithId = "SaleItem not found with ID: ";

    @Override
    public SaleItemDto createSaleItem(SaleItemDto saleItemDto) {
        SaleDto sale = saleService.findSale(saleItemDto.getSaleId());
        ProductDto product = productService.getProduct(saleItemDto.getProductId());

        SaleItem saleItem = modelMapper.map(saleItemDto, SaleItem.class);
        saleItem.setSale(modelMapper.map(sale, Sale.class));
        saleItem.setProduct(modelMapper.map(product, Product.class));

        SaleItem savedSaleItem = saleItemRepository.save(saleItem);
        return modelMapper.map(savedSaleItem, SaleItemDto.class);
    }

    @Override
    public SaleItemDto findSaleItemById(Long id) {
        SaleItem saleItem = saleItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(saleItemNotFoundWithId + id));
        return modelMapper.map(saleItem, SaleItemDto.class);
    }

    @Override
    public List<SaleItemDto> findAllSaleItems() {
        return saleItemRepository.findAll().stream()
                .map(saleItem -> modelMapper.map(saleItem, SaleItemDto.class))
                .toList();
    }

    @Override
    public SaleItemDto updateSaleItem(Long id, SaleItemDto saleItemDto) {
        SaleItem existingSaleItem = saleItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(saleItemNotFoundWithId + id));

        SaleDto sale = saleService.findSale(saleItemDto.getSaleId());
        ProductDto product = productService.getProduct(saleItemDto.getProductId());

        modelMapper.map(saleItemDto, existingSaleItem);
        existingSaleItem.setSale(modelMapper.map(sale, Sale.class));
        existingSaleItem.setProduct(modelMapper.map(product, Product.class));

        SaleItem updatedSaleItem = saleItemRepository.save(existingSaleItem);
        return modelMapper.map(updatedSaleItem, SaleItemDto.class);
    }

    @Override
    public boolean deleteSaleItemById(Long id) {
        SaleItem saleItem = saleItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(saleItemNotFoundWithId + id));
        saleItemRepository.delete(saleItem);
        return true;
    }
}
