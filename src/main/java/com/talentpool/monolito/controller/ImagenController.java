package com.talentpool.monolito.controller;

import com.talentpool.monolito.dto.ImagenDTO;
import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.model.Imagen;
import com.talentpool.monolito.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/imagenes")
@CrossOrigin("*")
public class ImagenController {

    @Autowired
    private ImagenService service;

    @GetMapping
    public List<Imagen> obtenerImagenes() {
        return service.obtenerImagenes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imagen> obtenerClientePorId(@PathVariable Integer id) {
        try {
            Imagen imagen = service.obtenerImagenPorId(id);
            return new ResponseEntity<>(imagen, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public void guardarImagen(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "contenido", required = false) MultipartFile file
    ) throws IOException {
        service.guardarImagen(id, file);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<?> actualizarImagen(@PathVariable Integer id, @Request) {
        try {
            service.actualizarCliente(id, cliente);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
}
