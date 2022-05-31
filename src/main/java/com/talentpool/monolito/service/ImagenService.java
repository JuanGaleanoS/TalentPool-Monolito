package com.talentpool.monolito.service;

import com.talentpool.monolito.dto.ImagenDTO;
import com.talentpool.monolito.model.Imagen;
import com.talentpool.monolito.repository.IImagenRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImagenService {

    @Autowired
    private IImagenRepository repository;

    public List<Imagen> obtenerImagenes() {
        return repository.findAll();
    }

    public Imagen obtenerImagenPorId(Integer id) {
        return repository.findById(id).get();
    }

    public void guardarImagen(Integer id, MultipartFile file) throws IOException {
        byte[] image = Base64.encodeBase64(file.getBytes());
        String imageBase64 = new String(image);

        Imagen imagen = new Imagen(id, "data:image/jpeg;base64," + imageBase64);
        repository.save(imagen);
    }

    public void actualizarImagen(Integer id, Imagen imagen) {
        Imagen imagenActualizar = obtenerImagenPorId(id);

        imagenActualizar.setContenido(imagen.getContenido());
        repository.save(imagenActualizar);
    }

    public void eliminarImagen(Integer id) {
        //Tener en cuenta que el borrado debe ir a borrar la imagen
        repository.deleteById(id);
    }
}
