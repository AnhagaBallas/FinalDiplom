package ru.netology.spring.security.Diploma.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.spring.security.Diploma.dtos.FileDto;
import ru.netology.spring.security.Diploma.entities.File;
import ru.netology.spring.security.Diploma.exceptions.AppError;
import ru.netology.spring.security.Diploma.service.MainService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;


    @GetMapping("/list")
    public List<FileDto> getList(@RequestHeader("auth-token") String authToken,
                                 @RequestParam String limit) {
        return mainService.getList(limit, authToken);
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String fileName, MultipartFile file) {

        mainService.uploadFile(fileName, file, authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam String filename) {
        mainService.deleteFileByName(authToken, filename);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/file")
    public File getFile(@RequestHeader("auth-token") String authToken,
                        @RequestParam String filename) {
        return mainService.getFile(authToken, filename);
    }

    @PutMapping("/file")
    public ResponseEntity<?> updateFile(@RequestHeader("auth-token") String authToken
            , @RequestParam String filename, @RequestBody FileDto file) {
        try {
            mainService.updateFile(filename, file, authToken);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppError appError) {
            return new ResponseEntity<>(appError.getMessage(), HttpStatus.valueOf(appError.getStatus()));
        }

    }
}