package com.talentpool.monolito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO implements Serializable {

    private Long id;

    @NotEmpty
    @NotNull
    @JsonProperty
    private String nombres;

    @NotEmpty
    @NotNull
    @JsonProperty
    private String apellidos;

    @JsonProperty("tipo_identificacion")
    private TipoIdentificacionDTO tipoIdentificacion;

    @NotEmpty
    @NotNull
    @JsonProperty
    private String identificacion;
    
    @JsonProperty("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @JsonProperty("ciudad_nacimiento")
    private CiudadDTO ciudadNacimiento;

    @JsonProperty("id_foto")
    private Long idFoto;
}
