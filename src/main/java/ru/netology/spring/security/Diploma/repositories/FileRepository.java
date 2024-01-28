package ru.netology.spring.security.Diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.spring.security.Diploma.entities.File;
import ru.netology.spring.security.Diploma.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
    List<File> findAllByUser(User user);
    Optional<File> findByUserAndFilename(User user, String fileName);
    void deleteFileByFilename(String fileName);
}