package ru.netology.spring.security.Diploma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.netology.spring.security.Diploma.dtos.JwtRequest;
import ru.netology.spring.security.Diploma.dtos.JwtResponse;
import ru.netology.spring.security.Diploma.dtos.RegistrationUserDto;
import ru.netology.spring.security.Diploma.dtos.UserDto;
import ru.netology.spring.security.Diploma.entities.User;
import ru.netology.spring.security.Diploma.exceptions.AppError;
import ru.netology.spring.security.Diploma.utils.JwtTokenUtils;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public Map<String, String> createAuthToken(@RequestBody JwtRequest authRequest) {
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        String token = jwtTokenUtils.generateToken(userDetails);
        return Map.of("auth-token", new JwtResponse(token).getToken());
    }


    public UserDto createNewUser(@RequestBody RegistrationUserDto registrationUserDto) throws AppError {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают");
        }
        if (userService.findByLogin(registrationUserDto.getLogin()).isPresent()) {
            throw new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует");
        }
        User user = userService.createNewUser(registrationUserDto);
        return new UserDto(user.getId(), user.getLogin());
    }
}
