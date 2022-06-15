package com.talentpool.monolito.data;

import com.talentpool.monolito.model.Ciudad;
import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.model.TipoIdentificacion;

import java.time.LocalDate;

public class DataClienteTest {

    public static final Cliente CLIENTE_UNO = Cliente.builder()
            .id(1L)
            .nombres("Ronald")
            .apellidos("Garcia")
            .tipoIdentificacion(TipoIdentificacion.builder().id(1L).build())
            .identificacion("114387865600")
            .fechaNacimiento(LocalDate.parse("1999-02-04"))
            .ciudadNacimiento(Ciudad.builder().id(1L).build())
            .idFoto(1L)
            .build();


    public static final Cliente CLIENTE_DOS = Cliente.builder()
            .id(2L)
            .nombres("Gabriel")
            .apellidos("Ortiz")
            .tipoIdentificacion(TipoIdentificacion.builder().id(1L).build())
            .identificacion("114387865601")
            .fechaNacimiento(LocalDate.parse("2000-04-11"))
            .ciudadNacimiento(Ciudad.builder().id(1L).build())
            .idFoto(2L)
            .build();


    public static final Cliente CLIENTE_TRES = Cliente.builder()
            .id(3L)
            .nombres("Jaime")
            .apellidos("Anaya")
            .tipoIdentificacion(TipoIdentificacion.builder().id(2L).build())
            .identificacion("114387865602")
            .fechaNacimiento(LocalDate.parse("1999-06-19"))
            .ciudadNacimiento(Ciudad.builder().id(2L).build())
            .idFoto(3L)
            .build();

    public static final Cliente CLIENTE_NO_IMAGE = Cliente.builder()
            .id(4L)
            .nombres("Adrian")
            .apellidos("Casanas")
            .tipoIdentificacion(TipoIdentificacion.builder().id(1L).build())
            .identificacion("114387865603")
            .fechaNacimiento(LocalDate.parse("1999-09-14"))
            .ciudadNacimiento(Ciudad.builder().id(1L).build())
            .idFoto(null)
            .build();

    public static final Cliente CLIENTE_INVALIDO = Cliente.builder()
            .id(null)
            .nombres(null)
            .apellidos(null)
            .tipoIdentificacion(TipoIdentificacion.builder().id(null).build())
            .identificacion(null)
            .fechaNacimiento(null)
            .ciudadNacimiento(Ciudad.builder().id(null).build())
            .idFoto(null)
            .build();
}
