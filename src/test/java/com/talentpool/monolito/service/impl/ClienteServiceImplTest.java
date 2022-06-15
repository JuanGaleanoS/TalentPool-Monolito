package com.talentpool.monolito.service.impl;

import com.talentpool.monolito.custom.exceptions.BusinessClienteException;
import com.talentpool.monolito.dto.ClienteDTO;
import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.repository.IClienteRepository;
import com.talentpool.monolito.repository.IImagenRepository;
import com.talentpool.monolito.service.IClienteService;
import com.talentpool.monolito.util.ObjectMapperUtils;
import com.talentpool.monolito.util.SequenceGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.talentpool.monolito.data.DataClienteTest.*;
import static com.talentpool.monolito.data.DataImagenTest.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteServiceImplTest {

    //Repository
    IClienteRepository mockClienteRepository = Mockito.mock(IClienteRepository.class);
    IImagenRepository mockImagenRepository = Mockito.mock(IImagenRepository.class);
    IClienteService clienteService;
    SequenceGeneratorService sequenceGeneratorService;
    MongoOperations mongoOperations;
    @Mock
    ModelMapper modelMapper;
    @Autowired
    ImagenServiceImpl imagenServiceImpl;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        sequenceGeneratorService = new SequenceGeneratorService(mongoOperations);
        imagenServiceImpl = new ImagenServiceImpl(mockImagenRepository, mockClienteRepository, sequenceGeneratorService);
        clienteService = new ClienteServiceImpl(mockClienteRepository, modelMapper, imagenServiceImpl);
    }

    @Test
    void testGetClientePorId() {
        // Mock del repository
        //Simular el llenado del repository con un mock y que se llene con la respuesta esperada.
        when(mockClienteRepository.findById(1L)).thenReturn(Optional.of(CLIENTE_UNO));

        // Llamado al método a probar
        Cliente clienteRespuesta = clienteService.getClientePorId(1L);

        // Assert
        assertAll(
                () -> assertNotNull(clienteRespuesta),
                () -> assertThat(clienteRespuesta).usingRecursiveComparison().isEqualTo(CLIENTE_UNO),
                () -> verify(mockClienteRepository).findById(1L)
        );

    }

    @Test
    void testGetClientePorIdException() {
        BusinessClienteException thrown = assertThrows(BusinessClienteException.class, () -> clienteService.getClientePorId(anyLong()));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    void testObtenerClientes() {

        List<Cliente> clienteListMock = new ArrayList<>();

        clienteListMock.add(CLIENTE_UNO);
        clienteListMock.add(CLIENTE_DOS);
        clienteListMock.add(CLIENTE_TRES);

        when(mockClienteRepository.findAll()).thenReturn(clienteListMock);

        List<ClienteDTO> clienteList = clienteService.obtenerClientes();

        assertAll(
                () -> assertNotNull(clienteList),
                () -> assertFalse(clienteList.isEmpty()),
                () -> assertEquals(3, clienteList.size()),
                () -> verify(mockClienteRepository).findAll()
        );

    }

    @Test
    void testObtenerClientesException() {
        when(mockClienteRepository.findAll()).thenReturn(Collections.emptyList());

        BusinessClienteException thrown = assertThrows(BusinessClienteException.class, () -> clienteService.obtenerClientes());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    void testObtenerClientePorId() {
        when(mockClienteRepository.findById(2L)).thenReturn(Optional.of(CLIENTE_DOS));

        ClienteDTO clienteDTO = clienteService.obtenerClientePorId(2L);

        assertAll(
                () -> assertNotNull(clienteDTO),
                () -> assertThat(clienteDTO).usingRecursiveComparison().isEqualTo(ObjectMapperUtils.map(CLIENTE_DOS, ClienteDTO.class)),
                () -> verify(mockClienteRepository).findById(2L)
        );

    }

    @Test
    void testObtenerClientePorTipoEIdentificacion() {

        when(mockClienteRepository.findTopByTipoIdentificacionAndIdentificacion(
                CLIENTE_TRES.getTipoIdentificacion(),
                CLIENTE_TRES.getIdentificacion()
        )).thenReturn(CLIENTE_TRES);

        ClienteDTO clienteDTO = clienteService.obtenerClientePorTipoEIdentificacion(
                CLIENTE_TRES.getTipoIdentificacion().getId(),
                CLIENTE_TRES.getIdentificacion()
        );

        assertAll(
                () -> assertNotNull(clienteDTO),
                () -> assertThat(clienteDTO).usingRecursiveComparison().isEqualTo(ObjectMapperUtils.map(CLIENTE_TRES, ClienteDTO.class)),
                () -> verify(mockClienteRepository).findTopByTipoIdentificacionAndIdentificacion(
                        CLIENTE_TRES.getTipoIdentificacion(),
                        CLIENTE_TRES.getIdentificacion()
                )
        );

    }

    @Test
    void testObtenerClientePorTipoEIdentificacionException() {

        BusinessClienteException thrown = assertThrows(
                BusinessClienteException.class,
                () -> clienteService.obtenerClientePorTipoEIdentificacion(anyLong(), anyString())
        );
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());

    }

    @Test
    void testObtenerClientesMayoresIgual() {

        Integer edad = 18;
        LocalDate fechaEdad = LocalDate.now().minusYears(edad);

        List<Cliente> clienteListMock = new ArrayList<>();

        clienteListMock.add(CLIENTE_UNO);
        clienteListMock.add(CLIENTE_DOS);
        clienteListMock.add(CLIENTE_TRES);

        when(mockClienteRepository.findByFechaNacimientoLessThanEqual(fechaEdad)).thenReturn(clienteListMock);

        List<ClienteDTO> clienteDTOList = clienteService.obtenerClientesMayoresIgual(edad);

        assertAll(
                () -> assertNotNull(clienteDTOList),
                () -> assertFalse(clienteDTOList.isEmpty()),
                () -> assertEquals(3, clienteDTOList.size()),
                () -> verify(mockClienteRepository).findByFechaNacimientoLessThanEqual(fechaEdad)
        );

    }

    @Test
    void testObtenerClientesMayoresIgualException() {

        BusinessClienteException thrown = assertThrows(
                BusinessClienteException.class,
                () -> clienteService.obtenerClientesMayoresIgual(99)
        );
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());

    }

    @Test
    void testGuardarCliente() {

        ClienteDTO cliente = ObjectMapperUtils.map(CLIENTE_UNO, ClienteDTO.class);

        when(mockClienteRepository.findClienteByIdentificacion(cliente.getIdentificacion())).thenReturn(CLIENTE_INVALIDO);

        when(mockClienteRepository.save(CLIENTE_UNO)).thenReturn(CLIENTE_UNO);

        String respuesta = clienteService.guardarCliente(cliente);

        assertAll(
                () -> assertNotNull(respuesta),
                () -> assertTrue(respuesta.contains("se creó exitosamente")),
                () -> verify(mockClienteRepository).findClienteByIdentificacion(cliente.getIdentificacion()),
                () -> verify(mockClienteRepository).save(CLIENTE_UNO)
        );

    }

    @Test
    void testGuardarClienteException() {

        ClienteDTO cliente = ObjectMapperUtils.map(CLIENTE_UNO, ClienteDTO.class);

        when(mockClienteRepository.findClienteByIdentificacion(cliente.getIdentificacion())).thenReturn(CLIENTE_UNO);

        BusinessClienteException thrown = assertThrows(
                BusinessClienteException.class,
                () -> clienteService.guardarCliente(cliente)
        );
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());

    }

    @Test
    void testActualizarCliente() {

        when(mockClienteRepository.findById(CLIENTE_DOS.getId())).thenReturn(Optional.of(CLIENTE_DOS));

        when(mockClienteRepository.save(CLIENTE_DOS)).thenReturn(CLIENTE_DOS);

        String respuesta = clienteService.actualizarCliente(ObjectMapperUtils.map(CLIENTE_DOS, ClienteDTO.class));

        assertAll(
                () -> assertNotNull(respuesta),
                () -> assertTrue(respuesta.contains("se actualizó exitosamente")),
                () -> verify(mockClienteRepository).findById(CLIENTE_DOS.getId()),
                () -> verify(mockClienteRepository).save(CLIENTE_DOS)
        );

    }

    @Test
    void testEliminarCliente() {

        when(mockClienteRepository.findById(CLIENTE_TRES.getId())).thenReturn(Optional.of(CLIENTE_TRES));

        when(mockImagenRepository.findById(CLIENTE_TRES.getId())).thenReturn(Optional.of(IMAGEN_TRES));

        when(mockClienteRepository.findClienteByIdFoto(CLIENTE_TRES.getIdFoto())).thenReturn(CLIENTE_TRES);

        String respuesta = clienteService.eliminarCliente(CLIENTE_TRES.getId());

        assertAll(
                () -> assertNotNull(respuesta),
                () -> assertTrue(respuesta.contains("se eliminó exitosamente")),
                () -> verify(mockClienteRepository).deleteById(CLIENTE_TRES.getId())
        );

    }

}