package com.talentpool.monolito.service.impl;

import com.talentpool.monolito.custom.exceptions.BusinessClienteException;
import com.talentpool.monolito.dto.ClienteDTO;
import com.talentpool.monolito.dto.ImagenDTO;
import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.model.Imagen;
import com.talentpool.monolito.repository.IClienteRepository;
import com.talentpool.monolito.repository.IImagenRepository;
import com.talentpool.monolito.service.IClienteService;
import com.talentpool.monolito.service.IImagenService;
import com.talentpool.monolito.util.ObjectMapperUtils;
import com.talentpool.monolito.util.SequenceGeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ImagenServiceImpl implements IImagenService {

    private static final String IMAGENES_NOT_FOUND_MESSAGE = "No hay imágenes registradas";
    private static final String IMAGEN_NOT_FOUND_MESSAGE = "La imagen con id %d no se encuentra registrada";
    private static final String CLIENTE_NOT_FOUND_MESSAGE = "El cliente con id %d no se encuentra registrado";
    private static final String IMAGEN_SUCCESS_CREATED = "La imagen %d se creó exitosamente";
    private static final String IMAGEN_SUCCESS_UPDATE = "La imagen con id %d se actualizó exitosamente";
    private static final String IMAGEN_SUCCESS_DELETE = "La imagen con id %d se eliminó exitosamente";
    private IImagenRepository imagenRepository;
    private IClienteRepository clienteRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public List<ImagenDTO> obtenerImagenes() {

        log.info("Ejecución método obtenerImagenes");
        List<Imagen> imagenList = imagenRepository.findAll();

        if (imagenList.isEmpty()) {
            log.error(IMAGENES_NOT_FOUND_MESSAGE);
            throw new BusinessClienteException(IMAGENES_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }

        return ObjectMapperUtils.mapAll(imagenList, ImagenDTO.class);
    }

    public ImagenDTO obtenerImagenPorId(Long id) {

        log.info("Ejecución método obtenerImagenPorId");
        Imagen imagen = getImagenPorId(id);

        return ObjectMapperUtils.map(imagen, ImagenDTO.class);
    }

    public String guardarImagen(Long idCliente, MultipartFile file) throws IOException {

        log.info("Ejecución método guardarImagen");

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new BusinessClienteException(
                String.format(CLIENTE_NOT_FOUND_MESSAGE, idCliente),
                HttpStatus.NOT_FOUND
        ));

        if (cliente.getIdFoto() != null) {
            log.error("El cliente ya cuenta con una imagen");
            throw new BusinessClienteException("El cliente ya cuenta con una imagen", HttpStatus.BAD_REQUEST);
        }

        Imagen imagen = Imagen.builder()
                .id(sequenceGeneratorService.generateSequence(Imagen.SEQUENCE_NAME))
                .imagen(multiPartFileToStringBase64(file))
                .build();
        imagenRepository.save(imagen);

        cliente.setIdFoto(imagen.getId());
        clienteRepository.save(cliente);

        return String.format(IMAGEN_SUCCESS_CREATED, imagen.getId());
    }

    public String actualizarImagen(Long idFoto, MultipartFile file) throws IOException {

        log.info("Ejecución método actualizarImagen");
        Imagen imagen = getImagenPorId(idFoto);

        imagen.setImagen(multiPartFileToStringBase64(file));
        imagenRepository.save(imagen);

        return String.format(IMAGEN_SUCCESS_UPDATE, imagen.getId());
    }

    public String eliminarImagen(Long idImagen) {

        log.info("Ejecución método actualizarImagen");

        getImagenPorId(idImagen);
        imagenRepository.deleteById(idImagen);

        Cliente cliente = clienteRepository.findClienteByIdFoto(idImagen);
        cliente.setIdFoto(null);
        clienteRepository.save(cliente);

        return String.format(IMAGEN_SUCCESS_DELETE, idImagen);
    }

    public String multiPartFileToStringBase64(MultipartFile file) throws IOException {

        String extensionFile = file.getOriginalFilename().split("\\.")[1];
        String base64Code = Base64.getEncoder().encodeToString(new Binary(BsonBinarySubType.BINARY, file.getBytes()).getData());

        return "data:image/" + extensionFile + ";base64," + base64Code;
    }

    public Imagen getImagenPorId(Long idImagen) {
        return imagenRepository.findById(idImagen)
                .orElseThrow(() -> {
                    log.error("No se encontró la imagen con id {}", idImagen);
                    return new BusinessClienteException(
                            String.format(IMAGEN_NOT_FOUND_MESSAGE, idImagen),
                            HttpStatus.NOT_FOUND
                    );
                });
    }
}
