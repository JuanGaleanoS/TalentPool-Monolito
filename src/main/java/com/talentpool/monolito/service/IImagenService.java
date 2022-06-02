package com.talentpool.monolito.service;

import com.talentpool.monolito.dto.ImagenDTO;
import com.talentpool.monolito.model.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImagenService {

    List<ImagenDTO> obtenerImagenes();

    ImagenDTO obtenerImagenPorId(Long id);

    Long guardarImagen(Long idCliente, MultipartFile file) throws IOException;

    void actualizarImagen(Long idFoto, MultipartFile file) throws IOException;

    void eliminarImagen(Long id);
}