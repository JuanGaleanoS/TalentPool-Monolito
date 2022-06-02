package com.talentpool.monolito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO {

    private Long id;
    @NotEmpty
    private String nombres;
    @NotEmpty
    private String apellidos;
    @NotEmpty
    @JsonProperty("tipo_identificacion")
    private TipoIdentificacionDTO tipoIdentificacion;
    @NotEmpty
    private String identificacion;
    @NotEmpty
    @JsonProperty("fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @NotEmpty
    @JsonProperty("ciudad_nacimiento")
    private CiudadDTO ciudadNacimiento;
    @JsonProperty("id_foto")
    private Long idFoto;
}
