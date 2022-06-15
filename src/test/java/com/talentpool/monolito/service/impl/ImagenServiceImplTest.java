package com.talentpool.monolito.service.impl;

import com.talentpool.monolito.custom.exceptions.BusinessClienteException;
import com.talentpool.monolito.dto.ClienteDTO;
import com.talentpool.monolito.dto.ImagenDTO;
import com.talentpool.monolito.model.Imagen;
import com.talentpool.monolito.repository.IClienteRepository;
import com.talentpool.monolito.repository.IImagenRepository;
import com.talentpool.monolito.service.IImagenService;
import com.talentpool.monolito.util.ObjectMapperUtils;
import com.talentpool.monolito.util.SequenceGeneratorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.talentpool.monolito.data.DataClienteTest.*;
import static com.talentpool.monolito.data.DataImagenTest.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ImagenServiceImplTest {

    @Mock
    IImagenRepository mockImagenRepository;
    @Mock
    IClienteRepository mockClienteRepository;
    @Mock
    SequenceGeneratorService sequenceGeneratorService;
    @InjectMocks
    ImagenServiceImpl imagenService;

    @Test
    void testGetImagenPorIdException() {

        BusinessClienteException thrown = assertThrows(BusinessClienteException.class, () -> imagenService.getImagenPorId(anyLong()));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    void testObtenerImagenes() {

        List<Imagen> imagenListMock = new ArrayList<>();

        imagenListMock.add(IMAGEN_UNO);
        imagenListMock.add(IMAGEN_DOS);
        imagenListMock.add(IMAGEN_TRES);

        when(mockImagenRepository.findAll()).thenReturn(imagenListMock);

        List<ImagenDTO> imagenDTOList = imagenService.obtenerImagenes();

        assertAll(
                () -> assertNotNull(imagenDTOList),
                () -> assertFalse(imagenDTOList.isEmpty()),
                () -> assertEquals(3, imagenDTOList.size()),
                () -> verify(mockImagenRepository).findAll()
        );

    }

    @Test
    void testObtenerImagenesException() {

        when(mockImagenRepository.findAll()).thenReturn(Collections.emptyList());

        BusinessClienteException thrown = assertThrows(BusinessClienteException.class, () -> imagenService.obtenerImagenes());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());

    }

    @Test
    void testMultiPartFileToStringBase64() throws IOException {

        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", null, "bar".getBytes());

        String base64 = imagenService.multiPartFileToStringBase64(file);

        assertAll(
                () -> assertFalse(base64.isEmpty()),
                () -> assertTrue(base64.contains("base64"))
        );

    }

    @Test
    void testObtenerImagenPorId() {

        when(mockImagenRepository.findById(2L)).thenReturn(Optional.of(IMAGEN_DOS));

        ImagenDTO imagenDTO = imagenService.obtenerImagenPorId(2L);

        assertAll(
                () -> assertNotNull(imagenDTO),
                () -> assertThat(imagenDTO).usingRecursiveComparison().isEqualTo(ObjectMapperUtils.map(IMAGEN_DOS, ImagenDTO.class)),
                () -> verify(mockImagenRepository).findById(2L)
        );

    }

    @Test
    void testGuardarImagen() throws IOException {

        when(mockClienteRepository.findById(CLIENTE_NO_IMAGE.getId())).thenReturn(Optional.of(CLIENTE_NO_IMAGE));

        when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(1L);

        when(mockImagenRepository.save(IMAGEN_UNO)).thenReturn(IMAGEN_UNO);

        when(mockClienteRepository.save(CLIENTE_NO_IMAGE)).thenReturn(CLIENTE_NO_IMAGE);

        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", null, "bar".getBytes());


        String respuesta = imagenService.guardarImagen(CLIENTE_NO_IMAGE.getId(), file);

        assertAll(
                () -> assertFalse(respuesta.isEmpty()),
                () -> assertTrue(respuesta.contains("se creó exitosamente")),
                () -> verify(mockClienteRepository).findById(CLIENTE_NO_IMAGE.getId()),
                () -> verify(mockClienteRepository).save(CLIENTE_NO_IMAGE),
                () -> verify(mockImagenRepository).save(IMAGEN_UNO)
        );

    }

    @Test
    void testGuardarImagenExceptionCliente() {

        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", null, "bar".getBytes());

        BusinessClienteException thrown = assertThrows(BusinessClienteException.class, () -> imagenService.guardarImagen(anyLong(), file));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());

    }

    @Test
    void testGuardarImagenExceptionImagen() {

        when(mockClienteRepository.findById(CLIENTE_UNO.getId())).thenReturn(Optional.of(CLIENTE_UNO));

        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", null, "bar".getBytes());

        BusinessClienteException thrown = assertThrows(BusinessClienteException.class, () -> imagenService.guardarImagen(CLIENTE_UNO.getId(), file));
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());

    }

    @Test
    void testActualizarImagen() throws IOException {

        when(mockImagenRepository.save(IMAGEN_DOS)).thenReturn(IMAGEN_DOS);
        when(mockImagenRepository.findById(IMAGEN_DOS.getId())).thenReturn(Optional.of(IMAGEN_DOS));

        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", null, "bar".getBytes());

        String respuesta = imagenService.actualizarImagen(IMAGEN_DOS.getId(), file);

        assertAll(
                () -> assertFalse(respuesta.isEmpty()),
                () -> assertTrue(respuesta.contains("se actualizó exitosamente"))
        );

    }

}
