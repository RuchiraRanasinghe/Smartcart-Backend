package smartcart.org.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {

    private Long id;

    @NotBlank(message = "Supplier name is required")
    private String name;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "\\d{10}", message = "Contact number must be exactly 10 digits")
    private String contactNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}
