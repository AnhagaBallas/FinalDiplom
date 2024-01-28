package ru.netology.spring.security.Diploma.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.spring.security.Diploma.dtos.FileDto;
import ru.netology.spring.security.Diploma.entities.File;
import ru.netology.spring.security.Diploma.entities.User;
import ru.netology.spring.security.Diploma.exceptions.AppError;
import ru.netology.spring.security.Diploma.repositories.FileRepository;
import ru.netology.spring.security.Diploma.repositories.UserRepository;
import ru.netology.spring.security.Diploma.utils.FileDtoUtils;
import ru.netology.spring.security.Diploma.utils.JwtTokenUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class MainService {
    private final FileRepository fileRepository;
    private final FileDtoUtils fileDtoUtils;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;

    @SneakyThrows
    public void uploadFile(String fileName, MultipartFile file, String authToken) {
        File fileToSave = new File();
        fileToSave.setFile(file.getBytes());
        fileToSave.setSize(file.getSize());
        fileToSave.setFilename(fileName);
        fileToSave.setUser(findByToken(authToken));
        fileToSave.setContentType(file.getContentType());
        log.info("Файл загружен успешно");
        fileRepository.save(fileToSave);

    }

    public List<FileDto> getList(String limit, String authToken) {
        log.info("Получен список файлов");
        return fileRepository.findAllByUser(findByToken(authToken))
                .stream()
                .map(fileDtoUtils::fileToFileDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateFile(String oldName, FileDto info, String authToken) throws AppError {
        User user = findByToken(authToken);
        Optional<File> file = fileRepository.findByUserAndFilename(user, oldName);
        if (file.isPresent()) {
            file.get().setFilename(info.getFilename());
        } else {
            throw new AppError(HttpStatus.BAD_REQUEST.value(), "Файл не найден");
        }
        fileRepository.save(file.get());
        log.info("Файл успешно переименован");
    }

    public File getFile(String authToken, String fileName) throws AppError {
        User user = findByToken(authToken);
        Optional<File> file = fileRepository.findByUserAndFilename(user, fileName);
        log.info("Файл успешно скачан");
        if (file.isPresent()) {
            return file.get();
        } else {
            throw new AppError(HttpStatus.BAD_REQUEST.value(), "Файл не найден");
        }

    }

    @SneakyThrows
    public User findByToken(String authToken) {
        Optional<User> user = userRepository.findByLogin(jwtTokenUtils.getUsername(getToken(authToken)));
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new AppError(404, "Пользователь не найден");
        }
    }

    public String getToken(String token) {
        return token.substring(6);
    }


    @Transactional
    public void deleteFileByName(String authToken, String name) {
        fileRepository.deleteFileByFilename(name);
        log.info("Файл успешно удален");
    }


}