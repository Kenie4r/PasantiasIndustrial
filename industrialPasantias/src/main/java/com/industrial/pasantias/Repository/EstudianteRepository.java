package com.industrial.pasantias.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.industrial.pasantias.Model.EstudianteEntity;

public interface EstudianteRepository extends JpaRepository<EstudianteEntity, String> {
    
    Optional<EstudianteEntity> findByCarnet(String carnet); 
    
    @Query(value = "SELECT * FROM ESTUDIANTE WHERE ID_CARRERA = :idCarrera", nativeQuery = true)
    List<EstudianteEntity> encontrarPorCarrera(int idCarrera);
}
