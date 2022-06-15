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
    private IClienteService iClienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obtenerClientes() {
        return new ResponseEntity<>(iClienteService.obtenerClientes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable Long id) {
        return new ResponseEntity<>(iClienteService.obtenerClientePorId(id), HttpStatus.OK);
    }

    @GetMapping("/{tipoIdentificacion}/{identificacion}")
    public ResponseEntity<ClienteDTO> obtenerClientePorTipoEIdentificacion(@PathVariable Long tipoIdentificacion, @PathVariable String identificacion) {
        return new ResponseEntity<>(iClienteService.obtenerClientePorTipoEIdentificacion(tipoIdentificacion, identificacion), HttpStatus.OK);
    }

    @GetMapping("/edad/{edad}")
    public ResponseEntity<List<ClienteDTO>> obtenerClientesMayoresIgual(@PathVariable Integer edad) {
        return new ResponseEntity<>(iClienteService.obtenerClientesMayoresIgual(edad), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> guardarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(iClienteService.guardarCliente(clienteDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> actualizarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(iClienteService.actualizarCliente(clienteDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        return new ResponseEntity<>(iClienteService.eliminarCliente(id), HttpStatus.OK);
    }

}
