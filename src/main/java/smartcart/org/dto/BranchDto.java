package smartcart.org.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {

    private Long id;

    @NotBlank(message = "Branch name is required")
    @Size(max = 100, message = "Branch name must be at most 100 characters")
    private String name;

    @Size(max = 255, message = "Location must be at most 255 characters")
    private String location;
}
