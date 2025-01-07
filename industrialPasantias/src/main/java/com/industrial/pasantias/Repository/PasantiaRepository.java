package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.industrial.pasantias.Model.Pasantia;

public interface PasantiaRepository extends JpaRepository<Pasantia, Integer> {
    
    Pasantia findByIdPasantia(int idPasantia);
    
    // Obtener ultimo correlativo por carrera
    @Query(value = "SELECT ISNULL(MAX(CORRELATIVO), 0) CORRELATIVO FROM PASANTIA WHERE ID_CARRERA = :idCarrera", nativeQuery = true)
    Integer obtenerUltimoCorrelativoPorCarrera(int idCarrera);
}
