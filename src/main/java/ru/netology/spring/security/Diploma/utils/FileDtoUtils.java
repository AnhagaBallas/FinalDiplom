package ru.netology.spring.security.Diploma.utils;

import org.springframework.stereotype.Component;
import ru.netology.spring.security.Diploma.dtos.FileDto;
import ru.netology.spring.security.Diploma.entities.File;


@Component
public class FileDtoUtils {

    public FileDto fileToFileDto(File file){
        return new FileDto(file.getFilename(), file.getSize());
    }
}
