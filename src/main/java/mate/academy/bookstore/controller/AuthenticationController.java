package mate.academy.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.UserLoginRequestDto;
import mate.academy.bookstore.dto.UserLoginResponseDto;
import mate.academy.bookstore.dto.UserRegistrationRequestDto;
import mate.academy.bookstore.dto.UserResponseDto;
import mate.academy.bookstore.exception.RegistrationException;
import mate.academy.bookstore.security.AuthenticationService;
import mate.academy.bookstore.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Authentication",
        description = "Endpoints for user registration and authentication"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with the provided registration details"
    )
    @RequestMapping("/registration")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @Operation(
            summary = "Authenticate a user",
            description = "Authenticates a user account with the provided details"
    )
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

}
