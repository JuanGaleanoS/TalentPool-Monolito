package com.talentpool.monolito.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    //GenerationType.IDENTITY Se basa en una columna de base de datos con incremento automático y
    //permite que la base de datos genere un nuevo valor con cada operación de inserción.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombres;

    @Column(nullable = false, length = 150)
    private String apellidos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_identificacion", nullable = false)
    private TipoIdentificacion tipo_identificacion;

    @Column(name = "identificacion", nullable = false, length = 50)
    private String identificacion;

    private LocalDate fecha_nacimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciudad_nacimiento", nullable = false)
    private Ciudad ciudad_nacimiento;

    private Long foto;
}
