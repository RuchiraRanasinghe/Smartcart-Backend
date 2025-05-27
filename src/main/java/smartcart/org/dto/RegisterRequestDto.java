package smartcart.org.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import smartcart.org.util.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterRequestDto {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be 4-20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Size(max = 50, message = "Full name must be at most 50 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(
            regexp = "^(\\+947\\d{8}|07\\d{8})$",
            message = "Phone number must be valid and start with +947 or 07 followed by 8 digits"
    )
    private String phoneNumber;

    @NotNull(message = "User role must be provided")
    private UserRole role;
}