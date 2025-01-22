package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.industrial.pasantias.Model.CambioDeCarrera;

@Repository
public interface CambioCarreraRepository extends JpaRepository<CambioDeCarrera, Integer> {

    @Modifying    
    @Query(value = "DELETE FROM LOG_CAMBIO_CARRERA WHERE CARNET = :carnet", nativeQuery = true)
    void deleteByCarnet(@Param("carnet") String carnet);

}
