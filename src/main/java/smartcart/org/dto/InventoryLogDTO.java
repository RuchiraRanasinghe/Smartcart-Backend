package smartcart.org.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryLogDTO {
    @NotNull
    private Long productId;

    @NotBlank
    private String changeType;

    @NotNull
    private Integer quantityChanged;

    private String note;

    @NotNull
    private Long createdBy;
}
