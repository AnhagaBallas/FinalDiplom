package ru.netology.spring.security.Diploma.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "files_id")
    private Integer id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "file")
    private byte[] file;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;


    @ManyToOne
    @JoinColumn(name = "login",referencedColumnName = "login")
    private User user;


}
