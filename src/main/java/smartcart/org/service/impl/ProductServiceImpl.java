package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.ProductDto;
import smartcart.org.dto.SupplierDto;
import smartcart.org.entity.Product;
import smartcart.org.entity.Supplier;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.repository.ProductRepository;
import smartcart.org.service.ProductService;
import smartcart.org.service.SupplierService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final SupplierService supplierService;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        SupplierDto supplierById = supplierService.getSupplierById(productDto.getSupplierId());
        Product product = modelMapper.map(productDto, Product.class);
        product.setSupplier(modelMapper.map(supplierById, Supplier.class));
        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @Override
    public ProductDto getProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        SupplierDto supplierById = supplierService.getSupplierById(productDto.getSupplierId());
        Product mappedProduct = modelMapper.map(productDto, Product.class);
        mappedProduct.setSupplier(modelMapper.map(supplierById, Supplier.class));
        return modelMapper.map(productRepository.save(mappedProduct), ProductDto.class);
    }

    @Override
    public Boolean deleteProduct(long id) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(existing);
        return true;
    }
}
