package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.industrial.pasantias.Model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
}