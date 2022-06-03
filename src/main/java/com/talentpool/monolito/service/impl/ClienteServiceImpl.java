package com.talentpool.monolito.service.impl;

import com.talentpool.monolito.dto.ClienteDTO;
import com.talentpool.monolito.custom.exceptions.BusinessClienteException;
import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.model.TipoIdentificacion;
import com.talentpool.monolito.repository.IClienteRepository;
import com.talentpool.monolito.service.IClienteService;
import com.talentpool.monolito.util.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private static final String CLIENTES_NOT_FOUND_MESSAGE = "No hay clientes registrados";
    private static final String CLIENTE_NOT_FOUND_MESSAGE = "El cliente con id %d no se encuentra registrado";
    private static final String CLIENTE_TIPO_IDEN_NOT_FOUND_MESSAGE = "El cliente con identificación %s y tipo %d no se encuentra registrado";
    private static final String CLIENTE_EDAD_NOT_FOUND_MESSAGE = "No hay clientes con una edad mayor o igual a %d años";
    private static final String CLIENTE_SUCCESS_CREATED = "El cliente %s se creó exitosamente";
    private static final String CLIENTE_SUCCESS_UPDATE = "El cliente con id %d se actualizó exitosamente";
    private static final String CLIENTE_SUCCESS_DELETE = "El cliente con id %d se eliminó exitosamente";
    private final IClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final ImagenServiceImpl imageService;

    @Override
    public List<ClienteDTO> obtenerClientes() {
        List<Cliente> clienteList = clienteRepository.findAll();

        if (clienteList.isEmpty()) {
            throw new BusinessClienteException(CLIENTES_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }

        return ObjectMapperUtils.mapAll(clienteList, ClienteDTO.class);
    }

    @Override
    public ClienteDTO obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessClienteException(
                        String.format(CLIENTE_NOT_FOUND_MESSAGE, id),
                        HttpStatus.NOT_FOUND
                ));

        return ObjectMapperUtils.map(cliente, ClienteDTO.class);
    }

    @Override
    public ClienteDTO obtenerClientePorTipoEIdentificacion(Long tipoIdentificacion, String identificacion) {

        TipoIdentificacion tipoIden = new TipoIdentificacion();
        tipoIden.setId(tipoIdentificacion);

        try {
            Cliente cliente = clienteRepository.findTopByTipoIdentificacionAndIdentificacion(tipoIden, identificacion);
            return ObjectMapperUtils.map(cliente, ClienteDTO.class);

        } catch (Exception e) {
            throw new BusinessClienteException(
                    String.format(CLIENTE_TIPO_IDEN_NOT_FOUND_MESSAGE, identificacion, tipoIdentificacion),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Override
    public List<ClienteDTO> obtenerClientesMayoresIgual(Integer edad) {

        LocalDate fechaEdad = LocalDate.now().minusYears(edad);

        try {
            List<Cliente> clienteList = clienteRepository.findByFechaNacimientoLessThanEqual(fechaEdad);
            return ObjectMapperUtils.mapAll(clienteList, ClienteDTO.class);

        } catch (Exception e) {
            throw new BusinessClienteException(
                    String.format(CLIENTE_EDAD_NOT_FOUND_MESSAGE, edad),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Override
    public String guardarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        clienteRepository.save(cliente);
        return String.format(CLIENTE_SUCCESS_CREATED, cliente.getNombres());
    }

    @Override
    public String actualizarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        clienteRepository.save(cliente);
        return String.format(CLIENTE_SUCCESS_UPDATE, cliente.getId());
    }

    @Override
    public String eliminarCliente(Long id) {

        try {
            Cliente cliente = clienteRepository.findById(id).get();

            if (cliente.getIdFoto() != null) {
                imageService.eliminarImagen(cliente.getIdFoto());
            }

            clienteRepository.deleteById(id);

            return String.format(CLIENTE_SUCCESS_DELETE, id);

        } catch (Exception e) {
            throw new BusinessClienteException(
                    String.format(CLIENTE_NOT_FOUND_MESSAGE, id),
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
