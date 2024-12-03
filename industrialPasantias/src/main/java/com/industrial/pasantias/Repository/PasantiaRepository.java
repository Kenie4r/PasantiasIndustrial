package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.industrial.pasantias.Model.Pasantia;

public interface PasantiaRepository extends JpaRepository<Pasantia, Integer> {
    
    // Buscar pasantia por id de pasante
    
}
