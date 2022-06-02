package com.talentpool.monolito.controller;

import com.talentpool.monolito.dto.ImagenDTO;
import com.talentpool.monolito.model.Imagen;
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
    public List<ImagenDTO> obtenerImagenes() {
        return iImagenService.obtenerImagenes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenDTO> obtenerImagenPorId(@PathVariable Long id) {
        try {
            ImagenDTO imagenDTO = iImagenService.obtenerImagenPorId(id);
            return new ResponseEntity<>(imagenDTO, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public void guardarImagen(
            @RequestParam(value = "idCliente") Long idCliente,
            @RequestParam(value = "foto") MultipartFile file
    ) throws IOException {
        iImagenService.guardarImagen(idCliente, file);
    }

    @PutMapping
    public void actualizarImagen(
            @RequestParam(value = "idFoto") Long idFoto,
            @RequestParam(value = "foto") MultipartFile file
    ) throws IOException {
        iImagenService.actualizarImagen(idFoto, file);
    }

    @DeleteMapping("/{idFoto}")
    public void eliminarImagen(@PathVariable Long idFoto) {
        iImagenService.eliminarImagen(idFoto);
    }
}
