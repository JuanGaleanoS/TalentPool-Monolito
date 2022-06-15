package com.talentpool.monolito.data;

import com.talentpool.monolito.model.Imagen;

public class DataImagenTest {

    public static final Imagen IMAGEN_UNO = Imagen.builder()
            .id(1L)
            .imagen("data:image/jpg;base64,YmFy")
            .build();

    public static final Imagen IMAGEN_DOS = Imagen.builder()
            .id(2L)
            .imagen("PRUEBA 2")
            .build();

    public static final Imagen IMAGEN_TRES = Imagen.builder()
            .id(3L)
            .imagen("PRUEBA")
            .build();

}
