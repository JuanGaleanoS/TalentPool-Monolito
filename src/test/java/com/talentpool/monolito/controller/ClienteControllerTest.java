package com.talentpool.monolito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentpool.monolito.dto.ClienteDTO;
import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.service.IClienteService;
import com.talentpool.monolito.util.ObjectMapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.talentpool.monolito.data.DataClienteTest.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IClienteService iClienteService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testObtenerClientes() throws Exception {

        List<Cliente> clienteList = Arrays.asList(
                CLIENTE_UNO,
                CLIENTE_DOS,
                CLIENTE_TRES
        );

        when(iClienteService.obtenerClientes()).thenReturn(ObjectMapperUtils.mapAll(clienteList, ClienteDTO.class));

        mockMvc.perform(get("/api/clientes/")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
        //.andExpect(content().json(objectMapper.writeValueAsString(clienteList)));

        verify(iClienteService).obtenerClientes();

    }

    @Test
    void testObtenerClientePorId() throws Exception {

        when(iClienteService.obtenerClientePorId(CLIENTE_DOS.getId())).thenReturn(ObjectMapperUtils.map(CLIENTE_DOS, ClienteDTO.class));

        mockMvc.perform(get("/api/clientes/{id}", CLIENTE_DOS.getId()).contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.nombres").value("Gabriel"));
        // Then
        verify(iClienteService).obtenerClientePorId(CLIENTE_DOS.getId());

    }

    @Test
    void testObtenerClientePorTipoEIdentificacion() throws Exception {

        when(iClienteService.obtenerClientePorTipoEIdentificacion(1L, CLIENTE_UNO.getIdentificacion()))
                .thenReturn(ObjectMapperUtils.map(CLIENTE_UNO, ClienteDTO.class));

        mockMvc.perform(get("/api/clientes/{tipoIdentificacion}/{identificacion}"
                        , 1L
                        , CLIENTE_UNO.getIdentificacion()
                ).contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombres").value("Ronald"));
        // Then
        verify(iClienteService).obtenerClientePorTipoEIdentificacion(1L, CLIENTE_UNO.getIdentificacion());

    }

    @Test
    void testObtenerClientesMayoresIgual() throws Exception {

        // Given
        List<Cliente> clienteList = Collections.singletonList(CLIENTE_UNO);

        // When
        when(iClienteService.obtenerClientesMayoresIgual(23)).thenReturn(ObjectMapperUtils.mapAll(clienteList, ClienteDTO.class));

        mockMvc.perform(get("/api/clientes/edad/{edad}", 23)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(iClienteService).obtenerClientesMayoresIgual(23);

    }

    @Test
    void testGuardarCliente() throws Exception {

        // Given
        ClienteDTO clienteDTO = ObjectMapperUtils.map(CLIENTE_TRES, ClienteDTO.class);
        clienteDTO.setId(null);
        clienteDTO.setFechaNacimiento(null); // Error con java 8 par las fechas

        // When
        when(iClienteService.guardarCliente(clienteDTO)).thenReturn(anyString());

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO))
                )
                // Then
                .andExpect(status().isCreated());

        verify(iClienteService).guardarCliente(clienteDTO);

    }

    @Test
    void testActualizarCliente() throws Exception {

        // Given
        ClienteDTO clienteDTO = ObjectMapperUtils.map(CLIENTE_DOS, ClienteDTO.class);
        clienteDTO.setFechaNacimiento(null); // Error con java 8 par las fechas

        // When
        when(iClienteService.actualizarCliente(clienteDTO)).thenReturn(anyString());

        mockMvc.perform(put("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO))
                )
                // Then
                .andExpect(status().isOk());

        verify(iClienteService).actualizarCliente(clienteDTO);

    }

    @Test
    void testEliminarCliente() throws Exception {
        // When
        when(iClienteService.eliminarCliente(CLIENTE_DOS.getId())).thenReturn(anyString());

        mockMvc.perform(delete("/api/clientes/{id}", CLIENTE_DOS.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                // Then
                .andExpect(status().isOk());

        verify(iClienteService).eliminarCliente(CLIENTE_DOS.getId());

    }

}
