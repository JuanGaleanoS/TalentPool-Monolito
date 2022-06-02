package com.talentpool.monolito.service.impl;

import com.talentpool.monolito.dto.ClienteDTO;
import com.talentpool.monolito.dto.ImagenDTO;
import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.model.Imagen;
import com.talentpool.monolito.repository.IClienteRepository;
import com.talentpool.monolito.repository.IImagenRepository;
import com.talentpool.monolito.service.IImagenService;
import com.talentpool.monolito.util.ObjectMapperUtils;
import com.talentpool.monolito.util.SequenceGeneratorService;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class ImagenServiceImpl implements IImagenService {

    private IImagenRepository imagenRepository;
    private IClienteRepository clienteRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public List<ImagenDTO> obtenerImagenes() {
        return ObjectMapperUtils.mapAll(imagenRepository.findAll(), ImagenDTO.class);
    }

    public ImagenDTO obtenerImagenPorId(Long id) {
        return ObjectMapperUtils.map(imagenRepository.findById(id).get(), ImagenDTO.class);
    }

    public Long guardarImagen(Long idCliente, MultipartFile file) throws IOException {

        //Validar que el Idcliente no tenga una imagen ya, de lo contrario lanzar excepcion

        Imagen imagen = Imagen.builder()
                .id(sequenceGeneratorService.generateSequence(Imagen.SEQUENCE_NAME))
                .imagen(multiPartFileToStringBase64(file))
                .build();
        imagenRepository.save(imagen);

        Cliente cliente = clienteRepository.findById(idCliente).get();
        cliente.setIdFoto(imagen.getId());
        clienteRepository.save(cliente);

        return imagen.getId();
    }

    public void actualizarImagen(Long idFoto, MultipartFile file) throws IOException {

        Imagen imagen = Imagen.builder()
                .id(idFoto)
                .imagen(multiPartFileToStringBase64(file))
                .build();
        imagenRepository.save(imagen);
    }

    public void eliminarImagen(Long idImagen) {

        imagenRepository.deleteById(idImagen);

        Cliente cliente = clienteRepository.findClienteByIdFoto(idImagen);
        cliente.setIdFoto(null);
        clienteRepository.save(cliente);

    }

    public String multiPartFileToStringBase64(MultipartFile file) throws IOException {

        String extensionFile = file.getOriginalFilename().split("\\.")[1];
        String base64Code = Base64.getEncoder().encodeToString(new Binary(BsonBinarySubType.BINARY, file.getBytes()).getData());

        return "data:image/" + extensionFile + ";base64," + base64Code;
    }
}
