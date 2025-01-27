package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.industrial.pasantias.Model.PasantiaPrograma;
import com.industrial.pasantias.Model.PK.PasantiaProgramaPK;
import java.util.List;
import java.util.Optional;

public interface PasantiaProgramaRepository extends JpaRepository<PasantiaPrograma, PasantiaProgramaPK> {
    @SuppressWarnings("null")
    Optional<PasantiaPrograma> findById(PasantiaProgramaPK id);

    @Query(value = "SELECT * FROM PASANTIA_PROGRAMA WHERE ID_PASANTIA = :idPasantia", nativeQuery = true)
    List<PasantiaPrograma> encontrarPorIdPasantia(int idPasantia);
}
