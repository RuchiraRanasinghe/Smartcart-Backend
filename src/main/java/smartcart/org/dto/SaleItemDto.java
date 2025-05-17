package smartcart.org.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemDto {

    private Long id;

    @NotNull(message = "Sale ID is required")
    private Long saleId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Min(value = 0, message = "Price per unit must be positive")
    private double pricePerUnit;

    @Min(value = 0, message = "Total price must be positive")
    private double totalPrice;
}
