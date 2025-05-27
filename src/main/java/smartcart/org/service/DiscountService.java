package smartcart.org.service;

import smartcart.org.dto.DiscountDto;

import java.util.List;

public interface DiscountService {

    DiscountDto createDiscount(DiscountDto discountDto);

    DiscountDto getDiscount(Long id);

    List<DiscountDto> getDiscounts();

    DiscountDto updateDiscount(Long id, DiscountDto discountDto);

    Boolean deleteDiscount(Long id);
}
