package com.industrial.pasantias.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.industrial.pasantias.Model.EmpresaPrograma;

@Repository()
public interface ProgramaRepository extends JpaRepository<EmpresaPrograma, Integer> {
    /*
     * @Query("SELECT ep FROM EmpresaPrograma ep WHERE ep.idEmpresa =0")
     * List<EmpresaPrograma> findProgramasByIdEmpresa(@Param("idEmpresa") Integer
     * idEmpresa);
     */
    @Query(value = "SELECT * FROM PROGRAMA WHERE ID_EMPRESA = :idEmpresa", nativeQuery = true)
    List<EmpresaPrograma> findProgramasByIdEmpresa(@Param("idEmpresa") Integer idEmpresa);

}
