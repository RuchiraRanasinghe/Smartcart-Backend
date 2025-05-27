package smartcart.org.dto;

import jakarta.validation.constraints.*;
import smartcart.org.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

    private Long id;

    @NotBlank(message = "Discount name is required")
    private String name;

    @DecimalMin(value = "0.0", message = "Percentage must be >= 0")
    @DecimalMax(value = "100.0", message = "Percentage must be <= 100")
    private double percentage;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotEmpty(message = "Applicable products list cannot be empty")
    private List<@NotNull(message = "Product cannot be null") Product> applicableProducts;
}
