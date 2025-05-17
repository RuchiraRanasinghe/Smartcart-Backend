package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.DiscountDto;
import smartcart.org.entity.Discount;
import smartcart.org.entity.Product;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.repository.DiscountRepository;
import smartcart.org.service.DiscountService;
import smartcart.org.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    String discountNotFoundWithIdMessage = "Discount not found with id: ";

    @Override
    public DiscountDto createDiscount(DiscountDto discountDto) {
        Discount discountEntity = getDiscountEntity(discountDto);
        return modelMapper.map(discountRepository.save(discountEntity), DiscountDto.class);
    }

    @Override
    public DiscountDto getDiscount(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(discountNotFoundWithIdMessage + id));
        return modelMapper.map(discount, DiscountDto.class);
    }

    @Override
    public List<DiscountDto> getDiscounts() {
        return discountRepository.findAll().stream()
                .map(discount -> modelMapper.map(discount, DiscountDto.class))
                .toList();
    }

    @Override
    public DiscountDto updateDiscount(Long id, DiscountDto discountDto) {
        Discount discountEntity = getDiscountEntity(discountDto);
        discountEntity.setId(id);
        return modelMapper.map(discountRepository.save(discountEntity), DiscountDto.class);
    }

    @Override
    public boolean deleteDiscount(Long id) {
        Discount existing = discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(discountNotFoundWithIdMessage + id));
        discountRepository.delete(existing);
        return true;
    }

    private Discount getDiscountEntity(DiscountDto discountDto) {
        Discount existing = new Discount();
        existing.setName(discountDto.getName());
        existing.setPercentage(discountDto.getPercentage());
        existing.setStartDate(discountDto.getStartDate());
        existing.setEndDate(discountDto.getEndDate());

        List<Product> validatedProducts = discountDto.getApplicableProducts().stream()
                .map(product -> modelMapper.map(productService.getProduct(product.getId()), Product.class))
                .toList();

        existing.setApplicableProducts(validatedProducts);
        return existing;
    }
}
