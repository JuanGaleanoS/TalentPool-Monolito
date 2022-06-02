package com.talentpool.monolito.service;

import com.talentpool.monolito.dto.ClienteDTO;

import java.util.List;

public interface IClienteService {

    List<ClienteDTO> obtenerClientes();

    ClienteDTO obtenerClientePorId(Long id);

    ClienteDTO obtenerClientePorTipoEIdentificacion(Long tipoIdentificacion, String identificacion);

    List<ClienteDTO> obtenerClientesMayoresIgual(Integer edad);

    String eliminarCliente(Long id);

    String actualizarCliente(ClienteDTO clienteDTO);

    String guardarCliente(ClienteDTO clienteDTO);
}
