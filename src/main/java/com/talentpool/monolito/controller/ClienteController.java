package com.talentpool.monolito.controller;

import com.talentpool.monolito.dto.ClienteDTO;
import com.talentpool.monolito.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private IClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obtenerClientes() {
        return new ResponseEntity<>(service.obtenerClientes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.obtenerClientePorId(id), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{tipoIdentificacion}/{identificacion}")
    public ResponseEntity<ClienteDTO> obtenerClientePorTipoEIdentificacion(
            @PathVariable Long tipoIdentificacion,
            @PathVariable String identificacion
    ) {
        try {
            ClienteDTO clienteDTO = service.obtenerClientePorTipoEIdentificacion(tipoIdentificacion, identificacion);
            return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/edad/{edad}")
    public ResponseEntity<List<ClienteDTO>> obtenerClientesMayoresIgual(@PathVariable Integer edad) {
        try {
            List<ClienteDTO> clienteDTO = service.obtenerClientesMayoresIgual(edad);
            return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(service.guardarCliente(clienteDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> actualizarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            return new ResponseEntity<>(service.actualizarCliente(clienteDTO), HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.eliminarCliente(id), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
