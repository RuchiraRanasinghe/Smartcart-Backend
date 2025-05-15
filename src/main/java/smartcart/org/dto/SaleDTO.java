package smartcart.org.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
    @NotNull
    private Long cashierId;

    private Long customerId;

    @NotBlank
    private String paymentType;

    @NotNull
    @Size(min = 1)
    private List<SaleItemDTO> items;
}
