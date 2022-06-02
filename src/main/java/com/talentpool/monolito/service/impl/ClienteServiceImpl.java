package com.talentpool.monolito.service.impl;

import com.talentpool.monolito.dto.ClienteDTO;
import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.model.TipoIdentificacion;
import com.talentpool.monolito.repository.IClienteRepository;
import com.talentpool.monolito.service.IClienteService;
import com.talentpool.monolito.util.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final ImagenServiceImpl imageService;

    @Override
    public List<ClienteDTO> obtenerClientes() {
        List<Cliente> clienteList = clienteRepository.findAll();
        return ObjectMapperUtils.mapAll(clienteList, ClienteDTO.class);
    }

    @Override
    public ClienteDTO obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        return ObjectMapperUtils.map(cliente, ClienteDTO.class);
    }

    @Override
    public ClienteDTO obtenerClientePorTipoEIdentificacion(Long tipoIdentificacion, String identificacion) {

        TipoIdentificacion tipoIden = new TipoIdentificacion();
        tipoIden.setId(tipoIdentificacion);

        Cliente cliente = clienteRepository.findTopByTipoIdentificacionAndIdentificacion(tipoIden, identificacion);
        return ObjectMapperUtils.map(cliente, ClienteDTO.class);
    }

    @Override
    public List<ClienteDTO> obtenerClientesMayoresIgual(Integer edad) {

        LocalDate fechaEdad = LocalDate.now().minusYears(edad);
        List<Cliente> clienteList = clienteRepository.findByFechaNacimientoLessThanEqual(fechaEdad);

        return ObjectMapperUtils.mapAll(clienteList, ClienteDTO.class);
    }

    @Override
    public String guardarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        clienteRepository.save(cliente);
        return String.format("El cliente %s, se creó exitosamente", cliente.getNombres());
    }

    @Override
    public String actualizarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        clienteRepository.save(cliente);
        return String.format("El cliente con id %d, se actualizó exitosamente", cliente.getId());
    }

    @Override
    public String eliminarCliente(Long id) {

        //Añadir validación si el cliente existe o no y trycatch
        Cliente cliente = clienteRepository.findById(id).get();

        if (cliente.getIdFoto() != null) {
            imageService.eliminarImagen(cliente.getIdFoto());
        }

        clienteRepository.deleteById(id);

        return String.format("Person with id %d, has been successfully deleted", id);
    }
}
