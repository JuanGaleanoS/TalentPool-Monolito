package com.talentpool.monolito.controller;

import com.talentpool.monolito.dto.ImagenDTO;
import com.talentpool.monolito.service.IImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/imagenes")
@CrossOrigin("*")
public class ImagenController {

    @Autowired
    private IImagenService iImagenService;

    @GetMapping
    public ResponseEntity<List<ImagenDTO>> obtenerImagenes() {
        return new ResponseEntity<>(iImagenService.obtenerImagenes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenDTO> obtenerImagenPorId(@PathVariable Long id) {
        return new ResponseEntity<>(iImagenService.obtenerImagenPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> guardarImagen(@RequestParam(value = "idCliente") Long idCliente, @RequestParam(value = "foto") MultipartFile file) throws IOException {
        return new ResponseEntity<>(iImagenService.guardarImagen(idCliente, file), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> actualizarImagen(@RequestParam(value = "idFoto") Long idFoto, @RequestParam(value = "foto") MultipartFile file) throws IOException {
        return new ResponseEntity<>(iImagenService.actualizarImagen(idFoto, file), HttpStatus.OK);
    }

    @DeleteMapping("/{idFoto}")
    public ResponseEntity<String> eliminarImagen(@PathVariable Long idFoto) {
        return new ResponseEntity<>(iImagenService.eliminarImagen(idFoto), HttpStatus.OK);
    }
}
