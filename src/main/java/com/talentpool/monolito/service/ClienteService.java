package com.talentpool.monolito.service;

import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository repository;

    public List<Cliente> listarClientes(){
        return repository.findAll();
    }

    public void guardarCliente(Cliente cliente){
        repository.save(cliente);
    }

    public void actualizarCliente(Long id, Cliente cliente){
        Cliente clienteActualizar = obtenerClientePorId(id);

        clienteActualizar.setNombres(cliente.getNombres());
        clienteActualizar.setApellidos(cliente.getApellidos());
        clienteActualizar.setTipo_identificacion(cliente.getTipo_identificacion());
        clienteActualizar.setIdentificacion(cliente.getIdentificacion());
        clienteActualizar.setFecha_nacimiento(cliente.getFecha_nacimiento());
        clienteActualizar.setCiudad_nacimiento(cliente.getCiudad_nacimiento());
        clienteActualizar.setFoto(cliente.getFoto());

        repository.save(clienteActualizar);
    }

    public Cliente obtenerClientePorId(Long id) {
        //validar si de verdad existe el registro con el id, con isPresent, para que lance una excepción
        return repository.findById(id).get();
    }

    public void eliminarCliente(Long id){
        //Tener en cuenta que el borrado debe ir a borrar la imagen
        repository.deleteById(id);
    }
}
