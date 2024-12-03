package com.industrial.pasantias.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.industrial.pasantias.Model.RolEntity;

@Repository()
public interface RolRepository extends JpaRepository<RolEntity, Integer> {

    @Query(value = "SELECT * FROM ROL WHERE ESTADO = :estado", nativeQuery = true)
    List<RolEntity> encontrarRolesActivos(@Param("estado") String estado);
}
