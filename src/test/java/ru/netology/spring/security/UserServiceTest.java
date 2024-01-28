package ru.netology.spring.security;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.spring.security.Diploma.dtos.JwtRequest;
import ru.netology.spring.security.Diploma.repositories.UserRepository;
import ru.netology.spring.security.Diploma.service.RoleService;
import ru.netology.spring.security.Diploma.service.UserService;
import ru.netology.spring.security.Diploma.utils.JwtTokenUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@AutoConfigureMockMvc
@Sql(value = {"test-db-script/db.changelog-users.sql", "test-db-script/db.changelog-add_user_roles.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(classes = {UserRepository.class, UserService.class})
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @MockBean
    RoleService roleService;
    @Autowired
    UserRepository userRepository;
    @MockBean
    PasswordEncoder passwordEncoder;
    JwtRequest jwtRequest = new JwtRequest("user@gmail.com", "100");


    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withExposedPorts(3306)
            .withDatabaseName("netology")
            .withUsername("root")
            .withPassword("mysql");



    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void testLoadUser() {
        assertEquals(jwtRequest.getLogin(), userService.loadUserByUsername(jwtRequest.getLogin()).getUsername());
    }


}
