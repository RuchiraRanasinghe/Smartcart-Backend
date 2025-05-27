package smartcart.org.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponseDto {
    private String token;
    private UserDto user;
}