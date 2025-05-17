package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.CustomerDto;
import smartcart.org.entity.Customer;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.repository.CustomerRepository;
import smartcart.org.service.CustomerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    String customerNotFoundWithIdMessage = "Customer not found with id: ";

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        Customer saved = customerRepository.save(customer);
        return modelMapper.map(saved, CustomerDto.class);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(customerNotFoundWithIdMessage + id));
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(c -> modelMapper.map(c, CustomerDto.class))
                .toList();
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(customerNotFoundWithIdMessage + id));

        modelMapper.map(customerDto, existing);
        Customer updated = customerRepository.save(existing);
        return modelMapper.map(updated, CustomerDto.class);
    }

    @Override
    public Boolean deleteCustomer(Long id) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(customerNotFoundWithIdMessage + id));

        customerRepository.delete(existing);
        return true;
    }
}
