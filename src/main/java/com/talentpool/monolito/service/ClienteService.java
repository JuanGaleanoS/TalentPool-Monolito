package com.talentpool.monolito.service;

import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
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

    public void actualizarCliente(Integer id, Cliente cliente){
        Cliente clienteActualizar = obtenerClientePorId(id);

        clienteActualizar.setNombres(cliente.getNombres());
        clienteActualizar.setApellidos(cliente.getApellidos());
        clienteActualizar.setTipoIdentificacion(cliente.getTipoIdentificacion());
        clienteActualizar.setIdentificacion(cliente.getIdentificacion());
        clienteActualizar.setEdad(cliente.getEdad());
        clienteActualizar.setCiudadNacimiento(cliente.getCiudadNacimiento());
        clienteActualizar.setFoto(cliente.getFoto());

        repository.save(clienteActualizar);
    }

    public Cliente obtenerClientePorId(Integer id) throws ConfigDataResourceNotFoundException {
        //validar si de verdad existe el registro con el id, con isPresent, para que lance una excepción
        return repository.findById(id).get();
    }

    public void eliminarCliente(Integer id){
        //Tener en cuenta que el borrado debe ir a borrar la imagen
        repository.deleteById(id);
    }
}
