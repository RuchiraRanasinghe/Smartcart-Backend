package smartcart.org.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuditLogDto {

    private Long id;

    @NotBlank(message = "Action is required")
    @Size(max = 100, message = "Action can't exceed 100 characters")
    private String action;

    @NotBlank(message = "Details are required")
    @Size(max = 500, message = "Details can't exceed 500 characters")
    private String details;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    @NotNull(message = "User ID is required")
    private Long userId;
}
