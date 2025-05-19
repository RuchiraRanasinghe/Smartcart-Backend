package smartcart.org.service;

import smartcart.org.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto getCustomerById(Long id);

    List<CustomerDto> getAllCustomers();

    CustomerDto updateCustomer(Long id, CustomerDto customerDto);

    Boolean deleteCustomer(Long id);
}
