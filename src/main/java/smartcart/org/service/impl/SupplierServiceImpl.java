package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.SupplierDto;
import smartcart.org.entity.Supplier;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.repository.SupplierRepository;
import smartcart.org.service.SupplierService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Override
    public SupplierDto createSupplier(SupplierDto supplierDto) {
        Supplier supplier = modelMapper.map(supplierDto, Supplier.class);
        return modelMapper.map(supplierRepository.save(supplier), SupplierDto.class);
    }

    @Override
    public SupplierDto getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return modelMapper.map(supplier, SupplierDto.class);
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(s -> modelMapper.map(s, SupplierDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDto updateSupplier(Long id, SupplierDto supplierDto) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));

        modelMapper.map(supplierDto, existing);
        return modelMapper.map(supplierRepository.save(existing), SupplierDto.class);
    }

    @Override
    public Boolean deleteSupplier(Long id) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        supplierRepository.delete(existing);
        return true;
    }
}
