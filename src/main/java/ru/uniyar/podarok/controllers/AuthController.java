package ru.uniyar.podarok.controllers;

import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.uniyar.podarok.dtos.JwtRequest;
import ru.uniyar.podarok.dtos.JwtResponse;
import ru.uniyar.podarok.dtos.RegistrationUserDto;
import ru.uniyar.podarok.dtos.UserDto;
import ru.uniyar.podarok.exceptions.UserAlreadyExist;
import ru.uniyar.podarok.services.AuthService;

@RestController
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("Открылась страница авторизации!");
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        try {
            UserDto userDto = authService.createNewUser(registrationUserDto);
            return ResponseEntity.ok(userDto);
        } catch (UserAlreadyExist e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            JwtResponse jwtResponse = authService.createAuthToken(authRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
