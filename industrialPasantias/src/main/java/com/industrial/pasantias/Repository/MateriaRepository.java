package com.industrial.pasantias.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.industrial.pasantias.Model.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {
    @Query(value = "SELECT * FROM MATERIA WHERE ID_CARRERA = :idCarrera AND ESTADO = 'A'", nativeQuery = true)
    List<Materia> findMateriasByIdCarrera(@Param("idCarrera") Integer idCarrera);

    @Query(value = "SELECT ID_CARRERA FROM MATERIA WHERE ID_MATERIA = :idMateria", nativeQuery = true)
    Integer findCarreraByIdMateria(@Param("idMateria") Integer idMateria);
}
