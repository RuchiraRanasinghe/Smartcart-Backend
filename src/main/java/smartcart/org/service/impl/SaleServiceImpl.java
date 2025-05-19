package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.SaleDto;
import smartcart.org.entity.Customer;
import smartcart.org.entity.Sale;
import smartcart.org.entity.User;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.repository.SaleRepository;
import smartcart.org.service.CustomerService;
import smartcart.org.service.SaleService;
import smartcart.org.service.AuthService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;

    private final AuthService authService;
    private final CustomerService customerService;

    String saleNotFoundWithIdMessage = "Sale not found with id: ";

    @Override
    public SaleDto createSale(SaleDto saleDto) {
        User cashier = modelMapper.map(authService.findById(saleDto.getCashierId()), User.class);
        Customer customer = modelMapper.map(customerService.getCustomerById(saleDto.getCustomerId()), Customer.class);

        Sale mappedSale = modelMapper.map(saleDto, Sale.class);
        mappedSale.setCashier(cashier);
        mappedSale.setCustomer(customer);

        return modelMapper.map(saleRepository.save(mappedSale), SaleDto.class);
    }

    @Override
    public SaleDto findSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(saleNotFoundWithIdMessage + id));
        return modelMapper.map(sale, SaleDto.class);
    }

    @Override
    public List<SaleDto> findAllSales() {
        return saleRepository.findAll().stream()
                .map(sale -> modelMapper.map(sale, SaleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SaleDto updateSale(Long id, SaleDto saleDto) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(saleNotFoundWithIdMessage + id));

        User cashier = modelMapper.map(authService.findById(saleDto.getCashierId()), User.class);
        Customer customer = modelMapper.map(customerService.getCustomerById(saleDto.getCustomerId()), Customer.class);

        modelMapper.map(saleDto, existingSale);
        existingSale.setId(id);
        existingSale.setCashier(cashier);
        existingSale.setCustomer(customer);

        return modelMapper.map(saleRepository.save(existingSale), SaleDto.class);
    }

    @Override
    public boolean deleteSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(saleNotFoundWithIdMessage + id));
        saleRepository.delete(sale);
        return true;
    }
}
