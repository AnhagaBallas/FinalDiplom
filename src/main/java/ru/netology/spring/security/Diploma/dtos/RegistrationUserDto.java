package ru.netology.spring.security.Diploma.dtos;

import lombok.Data;

@Data
public class RegistrationUserDto {
    private String password;
    private String confirmPassword;
    private String login;
}
