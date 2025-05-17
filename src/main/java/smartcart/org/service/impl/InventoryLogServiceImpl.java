package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.InventoryLogDto;
import smartcart.org.entity.InventoryLog;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.repository.InventoryLogRepository;
import smartcart.org.service.InventoryLogService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryLogServiceImpl implements InventoryLogService {

    private final InventoryLogRepository inventoryLogRepository;
    private final ModelMapper modelMapper;

    @Override
    public InventoryLogDto createLog(InventoryLogDto logDto) {
        InventoryLog log = modelMapper.map(logDto, InventoryLog.class);
        return modelMapper.map(inventoryLogRepository.save(log), InventoryLogDto.class);
    }

    @Override
    public InventoryLogDto getLogById(Long id) {
        InventoryLog log = inventoryLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found with id: " + id));
        return modelMapper.map(log, InventoryLogDto.class);
    }

    @Override
    public List<InventoryLogDto> getAllLogs() {
        return inventoryLogRepository.findAll()
                .stream()
                .map(log -> modelMapper.map(log, InventoryLogDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public InventoryLogDto updateLog(Long id, InventoryLogDto logDto) {
        InventoryLog existing = inventoryLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found with id: " + id));

        modelMapper.map(logDto, existing);
        return modelMapper.map(inventoryLogRepository.save(existing), InventoryLogDto.class);
    }

    @Override
    public Boolean deleteLog(Long id) {
        InventoryLog existing = inventoryLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found with id: " + id));
        inventoryLogRepository.delete(existing);
        return true;
    }
}
