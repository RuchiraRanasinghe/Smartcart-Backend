package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.InventoryLogDto;
import smartcart.org.dto.ProductDto;
import smartcart.org.entity.InventoryLog;
import smartcart.org.entity.Product;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.repository.InventoryLogRepository;
import smartcart.org.service.InventoryLogService;
import smartcart.org.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryLogServiceImpl implements InventoryLogService {

    private final InventoryLogRepository inventoryLogRepository;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    String logNotFoundWithIdMessage = "Inventory log not found with id: ";

    @Override
    public InventoryLogDto createLog(InventoryLogDto logDto) {
        ProductDto productById = productService.getProduct(logDto.getProductId());
        InventoryLog mappedInventoryLog = modelMapper.map(logDto, InventoryLog.class);
        mappedInventoryLog.setProduct(modelMapper.map(productById, Product.class));
        return modelMapper.map(inventoryLogRepository.save(mappedInventoryLog), InventoryLogDto.class);
    }

    @Override
    public InventoryLogDto getLogById(Long id) {
        InventoryLog log = inventoryLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(logNotFoundWithIdMessage + id));
        return modelMapper.map(log, InventoryLogDto.class);
    }

    @Override
    public List<InventoryLogDto> getAllLogs() {
        return inventoryLogRepository.findAll()
                .stream()
                .map(log -> modelMapper.map(log, InventoryLogDto.class))
                .toList();
    }

    @Override
    public InventoryLogDto updateLog(Long id, InventoryLogDto logDto) {
        ProductDto productById = productService.getProduct(logDto.getProductId());
        InventoryLog mappedInventoryLog = modelMapper.map(logDto, InventoryLog.class);
        mappedInventoryLog.setProduct(modelMapper.map(productById, Product.class));
        return modelMapper.map(inventoryLogRepository.save(mappedInventoryLog), InventoryLogDto.class);
    }

    @Override
    public Boolean deleteLog(Long id) {
        InventoryLog existing = inventoryLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(logNotFoundWithIdMessage + id));
        inventoryLogRepository.delete(existing);
        return true;
    }
}
