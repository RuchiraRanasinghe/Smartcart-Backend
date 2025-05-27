package smartcart.org.service;

import smartcart.org.dto.InventoryLogDto;
import java.util.List;

public interface InventoryLogService {

    InventoryLogDto createLog(InventoryLogDto logDto);

    InventoryLogDto getLogById(Long id);

    List<InventoryLogDto> getAllLogs();

    InventoryLogDto updateLog(Long id, InventoryLogDto logDto);

    Boolean deleteLog(Long id);
}
