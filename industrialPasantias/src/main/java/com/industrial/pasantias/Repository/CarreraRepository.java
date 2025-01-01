package com.industrial.pasantias.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.industrial.pasantias.Model.Carrera;

public interface CarreraRepository extends JpaRepository<Carrera, Integer> {

    @Query(value = "SELECT * FROM CARRERA WHERE ESTADO = :estado", nativeQuery = true)
    List<Carrera> encontrarCarrerasActivas(@Param("estado") String estado);
}
