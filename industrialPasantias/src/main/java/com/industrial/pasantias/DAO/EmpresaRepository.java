package com.industrial.pasantias.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.Model.Empresa;
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
