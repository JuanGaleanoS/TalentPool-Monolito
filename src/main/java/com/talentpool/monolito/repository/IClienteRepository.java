package com.talentpool.monolito.repository;

import com.talentpool.monolito.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
}
