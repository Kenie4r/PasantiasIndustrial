package com.industrial.pasantias.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.industrial.pasantias.Model.Rubro;

public interface RubroRepository extends JpaRepository<Rubro, Integer> {
    //
    @Query(value = "SELECT * FROM RUBRO_EMPRESA WHERE ESTADO = :estado", nativeQuery = true)
    List<Rubro> encontrarRubrosActivos(@Param("estado") String estado);
}
