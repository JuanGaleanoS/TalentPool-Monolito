package com.talentpool.monolito.controller;

import com.talentpool.monolito.dto.ClienteDTO;
import com.talentpool.monolito.service.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes registrados",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "No hay clientes registrados",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obtenerClientes() {
        return new ResponseEntity<>(iClienteService.obtenerClientes(), HttpStatus.OK);
    }

    @Operation(summary = "Consultar cliente por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteDTO.class))}),
            @ApiResponse(responseCode = "400", description = "El id enviado no es un dato válido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El cliente no se encuentra registrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable Long id) {
        return new ResponseEntity<>(iClienteService.obtenerClientePorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Consultar cliente por tipo de identificación e identificación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteDTO.class))}),
            @ApiResponse(responseCode = "400", description = "El tipo de identificación o identificación enviado no es un dato válido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El cliente no se encuentra registrado",
                    content = @Content)
    })
    @GetMapping("/{tipoIdentificacion}/{identificacion}")
    public ResponseEntity<ClienteDTO> obtenerClientePorTipoEIdentificacion(@PathVariable Long tipoIdentificacion, @PathVariable String identificacion) {
        return new ResponseEntity<>(iClienteService.obtenerClientePorTipoEIdentificacion(tipoIdentificacion, identificacion), HttpStatus.OK);
    }

    @Operation(summary = "Listar clientes con edad mayor o igual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes registrados",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "La edad enviada no es un dato válido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No hay clientes registrados con esa edad",
                    content = @Content)
    })
    @GetMapping("/edad/{edad}")
    public ResponseEntity<List<ClienteDTO>> obtenerClientesMayoresIgual(@PathVariable Integer edad) {
        return new ResponseEntity<>(iClienteService.obtenerClientesMayoresIgual(edad), HttpStatus.OK);
    }

    @Operation(summary = "Registrar cliente nuevo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente registrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(description = "Mensaje cliente registrado"))}),
            @ApiResponse(responseCode = "400", description = "El cliente a registrar no tiene una estructura válida",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> guardarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(iClienteService.guardarCliente(clienteDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(description = "Mensaje cliente actualizado"))}),
            @ApiResponse(responseCode = "400", description = "El cliente a actualizar no tiene una estructura válida",
                    content = @Content)
    })
    @PutMapping
    public ResponseEntity<?> actualizarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(iClienteService.actualizarCliente(clienteDTO), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(description = "Mensaje cliente eliminado"))}),
            @ApiResponse(responseCode = "400", description = "El id cliente no es válido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El id enviado no se encuentra registrado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        return new ResponseEntity<>(iClienteService.eliminarCliente(id), HttpStatus.OK);
    }

}
