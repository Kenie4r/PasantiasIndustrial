package com.industrial.pasantias.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.industrial.pasantias.Model.EstudianteEntity;
import com.industrial.pasantias.Model.PasantiaPrograma;

public interface EstudianteRepository extends JpaRepository<EstudianteEntity, String> {

    Optional<EstudianteEntity> findByCarnet(String carnet);

    @Query(value = "SELECT * FROM ESTUDIANTE WHERE ID_CARRERA = :idCarrera", nativeQuery = true)
    List<EstudianteEntity> encontrarPorCarrera(int idCarrera);

    @Query(value = "SELECT * FROM PASANTIA_PROGRAMA WHERE ID_PASANTIA = :idPasantia", nativeQuery = true)
    List<PasantiaPrograma> encontrarPorIdPasantia(int idPasantia);

    @Query(value = "SELECT * FROM ESTUDIANTE WHERE CARNET = :carnet", nativeQuery = true)
    EstudianteEntity encontrarPorCarnet(String carnet);
}
