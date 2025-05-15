package smartcart.org.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartcart.org.entity.Product;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

    private Long id;

    @NotBlank(message = "Discount name is required")
    private String name;

    @DecimalMin(value = "0.0", inclusive = true, message = "Percentage must be >= 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Percentage must be <= 100")
    private double percentage;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotEmpty(message = "Applicable products list cannot be empty")
    private List<@NotNull(message = "Product cannot be null") Product> applicableProducts;
}
