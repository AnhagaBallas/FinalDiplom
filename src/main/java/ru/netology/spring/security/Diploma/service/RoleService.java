package ru.netology.spring.security.Diploma.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.netology.spring.security.Diploma.entities.Role;
import ru.netology.spring.security.Diploma.exceptions.AppError;
import ru.netology.spring.security.Diploma.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @SneakyThrows
    public Role getUserRole() {
        if(roleRepository.findByName("ROLE_USER").isPresent()){
            return roleRepository.findByName("ROLE_USER").get();
        }else {
            throw new AppError(HttpStatus.NO_CONTENT.value(), "Роль не найдена");
        }
    }
}
