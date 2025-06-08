package smartcart.org.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.*;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.response.ApiResponse;
import smartcart.org.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody RegisterRequestDto registerDto) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), authService.create(registerDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginDto) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse<>(true, HttpStatus.OK.value(), authService.login(loginDto)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false,HttpStatus.NOT_FOUND.value(), null,e.getMessage()));
        }


    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Object>> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) {
        authService.forgotPassword(forgotPasswordRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), null,"OTP sent to your email"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Object>> resetPassword(@Valid @RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        authService.resetPassword(resetPasswordRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), null,"Password Reset Successfully."));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), authService.findAll()));
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), authService.findById(id)));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), authService.update(id, userDto)));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Boolean>> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), authService.deleteById(id)));
    }
}