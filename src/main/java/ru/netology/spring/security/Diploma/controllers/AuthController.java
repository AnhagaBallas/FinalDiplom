package ru.netology.spring.security.Diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.spring.security.Diploma.dtos.JwtRequest;
import ru.netology.spring.security.Diploma.dtos.RegistrationUserDto;
import ru.netology.spring.security.Diploma.dtos.UserDto;
import ru.netology.spring.security.Diploma.exceptions.AppError;
import ru.netology.spring.security.Diploma.service.AuthService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            Map<String, String> token = authService.createAuthToken(authRequest);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        try {
            UserDto userDto = authService.createNewUser(registrationUserDto);
            return ResponseEntity.ok(userDto);
        } catch (AppError appError) {
            return new ResponseEntity<>(appError.getMessage(), HttpStatus.valueOf(appError.getStatus()));
        }

    }
}