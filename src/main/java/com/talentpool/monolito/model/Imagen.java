package com.talentpool.monolito.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "imagen")
public class Imagen {
    @Transient
    public static final String SEQUENCE_NAME = "image_id";
    @Id
    private Long id;
    private String imagen;
}
