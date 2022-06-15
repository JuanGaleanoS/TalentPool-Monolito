package com.talentpool.monolito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentpool.monolito.dto.ClienteDTO;
import com.talentpool.monolito.dto.ImagenDTO;
import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.model.Imagen;
import com.talentpool.monolito.service.IImagenService;
import com.talentpool.monolito.util.ObjectMapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static com.talentpool.monolito.data.DataClienteTest.*;
import static com.talentpool.monolito.data.DataImagenTest.*;
import static com.talentpool.monolito.data.DataImagenTest.IMAGEN_DOS;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ImagenController.class)
public class ImagenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IImagenService iImagenService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testObtenerImagenes() throws Exception {

        List<Imagen> imagenList = Arrays.asList(
                IMAGEN_UNO,
                IMAGEN_DOS,
                IMAGEN_TRES
        );

        when(iImagenService.obtenerImagenes()).thenReturn(ObjectMapperUtils.mapAll(imagenList, ImagenDTO.class));

        mockMvc.perform(get("/api/imagenes/")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        verify(iImagenService).obtenerImagenes();

    }

    @Test
    void testObtenerImagenPorId() throws Exception {

        when(iImagenService.obtenerImagenPorId(IMAGEN_DOS.getId())).thenReturn(ObjectMapperUtils.map(IMAGEN_DOS, ImagenDTO.class));

        mockMvc.perform(get("/api/imagenes/{id}", IMAGEN_DOS.getId()).contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.imagen").isString());
        // Then
        verify(iImagenService).obtenerImagenPorId(IMAGEN_DOS.getId());

    }

    @Test
    void testEliminarCliente() throws Exception {
        // When
        when(iImagenService.eliminarImagen(IMAGEN_DOS.getId())).thenReturn(anyString());

        mockMvc.perform(delete("/api/imagenes/{id}", IMAGEN_DOS.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                // Then
                .andExpect(status().isOk());

        verify(iImagenService).eliminarImagen(IMAGEN_DOS.getId());

    }

    @Test
    void testGuardarImagen() throws Exception {

        // Given
        MockMultipartFile file = new MockMultipartFile("foto", "orig.jpg", null, "bar".getBytes());

        // When
        when(iImagenService.guardarImagen(CLIENTE_UNO.getId(), file)).thenReturn(String.format("La imagen %d se creó exitosamente", IMAGEN_UNO.getId()));

        mockMvc.perform(multipart("/api/imagenes/")
                        .file(file)
                        .param("idCliente", CLIENTE_UNO.getId().toString())
                )
                // Then
                .andExpect(status().isCreated());

        verify(iImagenService).guardarImagen(CLIENTE_UNO.getId(), file);

    }

    @Test
    void testActualizarImagen() throws Exception {

        // Given
        MockMultipartFile file = new MockMultipartFile("foto", "orig.jpg", null, "bar".getBytes());

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/api/imagenes/");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        // When
        when(iImagenService.actualizarImagen(IMAGEN_UNO.getId(), file)).thenReturn(String.format("La imagen %d se actualizó exitosamente", IMAGEN_UNO.getId()));

        mockMvc.perform(builder
                        .file(file)
                        .param("idFoto", IMAGEN_UNO.getId().toString())
                )
                // Then
                .andExpect(status().isOk());

        verify(iImagenService).actualizarImagen(IMAGEN_UNO.getId(), file);

    }

}
