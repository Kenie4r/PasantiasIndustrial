package com.industrial.pasantias.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.industrial.pasantias.Model.EstudianteEntity;
import com.industrial.pasantias.Model.Pasantia;

public interface PasantiaRepository extends JpaRepository<Pasantia, Integer> {
    
    Pasantia findByIdPasantia(int idPasantia);
    
    // Obtener ultimo correlativo por carrera
    @Query(value = "SELECT ISNULL(MAX(CORRELATIVO), 0) CORRELATIVO FROM PASANTIA WHERE ID_CARRERA = :idCarrera", nativeQuery = true)
    Integer obtenerUltimoCorrelativoPorCarrera(int idCarrera);

    // Obtener las horas totales de una pasantia
    @Query(value = "SELECT SUM(M.HORAS) FROM PASANTIA PA INNER JOIN PASANTIA_PROGRAMA PP ON PA.ID_PASANTIA = PP.ID_PASANTIA INNER JOIN PROGRAMA P ON PP.ID_PROGRAMA = P.ID_PROGRAMA INNER JOIN MATERIA M ON P.ID_MATERIA = M.ID_MATERIA WHERE PA.ID_PASANTIA = :idPasantia", nativeQuery = true)
    Integer obtenerHorasTotales(int idPasantia);

    // Obtener practicas profesionales activas
    @Query(value = "SELECT COUNT(ID_PASANTIA) FROM pasantia WHERE ESTADO = 'E'", nativeQuery = true)
    Integer obtenerPracticasProfesionalesActivas();

    // Obtener practicas profesionales activas ingenieria
    @Query(value = "SELECT COUNT(ID_PASANTIA) FROM pasantia WHERE ESTADO = 'E' AND ID_CARRERA = 1", nativeQuery = true)
    Integer obtenerPracticasProfesionalesActivasIngenieria();

    // Obtener practicas profesionales activas tecnico
    @Query(value = "SELECT COUNT(ID_PASANTIA) FROM pasantia WHERE ESTADO = 'E' AND ID_CARRERA = 3", nativeQuery = true)
    Integer obtenerPracticasProfesionalesActivasTecnico();

    //Obtener todas  por el estudinante
    List<Pasantia> findByEstudiante(EstudianteEntity estudiante);

    // Obtener ultimas practicas profesionales
    @Query(value = "SELECT TOP 5 * FROM PASANTIA ORDER BY ID_PASANTIA DESC", nativeQuery = true)
    List<Pasantia> obtenerUltimasPracticasProfesionales();
}
