package com.talentpool.monolito.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImagenDTO {

    private Integer id;
    private MultipartFile file;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
