package smartcart.org.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForgotPasswordRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}