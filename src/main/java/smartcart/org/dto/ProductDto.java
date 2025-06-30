package smartcart.org.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name is mandatory")
    private String name;

    @NotBlank(message = "Barcode is mandatory")
    private String barcode;

    @NotBlank(message = "Category Cannot Be Empty")
    private String category;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    @PositiveOrZero(message = "Cost price must be zero or positive")
    private double costPrice;

    @Positive(message = "Selling price must be positive")
    private double sellingPrice;

    @FutureOrPresent(message = "Expire Date Must be a Future")
    private Date expiryDate;

    @NotNull(message = "Supplier Id is required")
    private Long supplierId;
}
