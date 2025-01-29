package com.industrial.pasantias.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.industrial.pasantias.Model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    @Query(value = "SELECT TOP 5 RE.DESCRIPCION AS rubro, COUNT(E.ID_EMPRESA) AS cantidad " +
            "FROM EMPRESA E " +
            "INNER JOIN RUBRO_EMPRESA RE ON E.ID_RUBRO = RE.ID_RUBRO " +
            "GROUP BY RE.DESCRIPCION " +
            "ORDER BY MAX(E.FECHA_CREA) DESC", nativeQuery = true)
    List<Map<String, Object>> findEmpresaCountByRubro();

}
