package smartcart.org.service;

import smartcart.org.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    ProductDto getProduct(long id);

    List<ProductDto> getProducts();

    ProductDto updateProduct(ProductDto productDto);

    Boolean deleteProduct(long id);
}
