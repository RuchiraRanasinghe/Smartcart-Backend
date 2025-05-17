package smartcart.org.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartcart.org.util.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

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

    @Pattern(regexp = "^\\+?94\\d{9}$", message = "Phone number must be a valid Sri Lankan number starting with country code +94")
    private String phoneNumber;

    @NotNull(message = "User role must be provided")
    private UserRole role;

    private boolean active;
}
