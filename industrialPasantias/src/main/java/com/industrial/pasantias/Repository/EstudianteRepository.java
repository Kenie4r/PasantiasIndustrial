package com.industrial.pasantias.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.industrial.pasantias.Model.EstudianteEntity;

public interface EstudianteRepository extends JpaRepository<EstudianteEntity, String> {
    
    Optional<EstudianteEntity> findByCarnet(String carnet); 
    
}
