package ru.netology.spring.security.Diploma.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class FileDto {
    private String filename;
    private Long size;
}
