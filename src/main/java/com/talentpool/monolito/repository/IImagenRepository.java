package com.talentpool.monolito.repository;

import com.talentpool.monolito.model.Imagen;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IImagenRepository extends MongoRepository<Imagen, Integer> {
}
