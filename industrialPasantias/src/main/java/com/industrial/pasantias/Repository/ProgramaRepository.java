package com.industrial.pasantias.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.industrial.pasantias.Model.Programa;
import com.industrial.pasantias.Model.ProgramaResumen;

@Repository()
public interface ProgramaRepository extends JpaRepository<Programa, Integer> {
    /*
     * @Query("SELECT ep FROM EmpresaPrograma ep WHERE ep.idEmpresa =0")
     * List<EmpresaPrograma> findProgramasByIdEmpresa(@Param("idEmpresa") Integer
     * idEmpresa);
     */
    @Query(value = "SELECT * FROM PROGRAMA WHERE ID_EMPRESA = :idEmpresa", nativeQuery = true)
    List<Programa> findProgramasByIdEmpresa(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT FORMAT(FECHA_INI, 'yyyy-MM') AS mes, TIPO_PROGRAMA AS tipoPrograma, COUNT(*) AS cantidad " +
            "FROM PROGRAMA " +
            "WHERE TIPO_PROGRAMA IN ('Empresa', 'Asignatura') AND FECHA_INI LIKE CONCAT('%', :fechaIni, '%')" +
            "GROUP BY FORMAT(FECHA_INI, 'yyyy-MM'), TIPO_PROGRAMA " +
            "ORDER BY FORMAT(FECHA_INI, 'yyyy-MM'), TIPO_PROGRAMA", nativeQuery = true)
    List<ProgramaResumen> findProgramasByMesAndTipoPrograma(@Param("fechaIni") Integer fechaIni);

}
