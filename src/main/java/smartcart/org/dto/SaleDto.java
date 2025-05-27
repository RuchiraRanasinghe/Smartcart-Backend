package smartcart.org.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smartcart.org.util.PaymentType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {

    private Long id;

    @NotNull(message = "Sale time is required")
    @PastOrPresent(message = "Sale time cannot be in the future")
    private LocalDateTime saleTime;

    @NotNull(message = "Cashier is required")
    private Long cashierId;

    @NotNull(message = "Customer is required")
    private Long customerId;

    @PositiveOrZero(message = "Total amount must be zero or positive")
    private double totalAmount;

    @PositiveOrZero(message = "Discount must be zero or positive")
    private double discount;

    @PositiveOrZero(message = "Paid amount must be zero or positive")
    @DecimalMin(value = "0.0")
    private double paidAmount;

    @NotNull(message = "Payment type is required")
    private PaymentType paymentType;
}
