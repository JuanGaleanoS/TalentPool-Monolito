package com.talentpool.monolito.repository;

import com.talentpool.monolito.model.Cliente;
import com.talentpool.monolito.model.TipoIdentificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findTopByTipoIdentificacionAndIdentificacion(TipoIdentificacion tipoIdentificacion, String identificacion);

    List<Cliente> findByFechaNacimientoLessThanEqual(LocalDate fechaEdad);

    Cliente findClienteByIdFoto(Long idFoto);

    Cliente findClienteByIdentificacion(String identificacion);
}
