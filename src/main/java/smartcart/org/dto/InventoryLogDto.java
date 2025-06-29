package smartcart.org.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryLogDto {

    private Long id;

    @NotNull(message = "Product must be provided")
    private Long productId;

    @NotNull(message = "Change amount is required")
    @Min(value = -10000, message = "Change amount cannot be less than -10000")
    @Max(value = 10000, message = "Change amount cannot be more than 10000")
    private Integer changeAmount;

    @NotBlank(message = "Reason must not be blank")
    private String reason;

    private LocalDateTime timestamp;

    private Long updatedById;
}
