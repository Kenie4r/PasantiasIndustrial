package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.industrial.pasantias.Model.CambioDeCarrera;

@Repository
public interface CambioCarreraRepository extends JpaRepository<CambioDeCarrera, Integer> {

    

}
