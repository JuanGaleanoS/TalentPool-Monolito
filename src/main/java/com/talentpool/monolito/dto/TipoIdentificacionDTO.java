package com.talentpool.monolito.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoIdentificacionDTO implements Serializable {
    @NotEmpty
    private Integer id;
    private String nombre;
}
