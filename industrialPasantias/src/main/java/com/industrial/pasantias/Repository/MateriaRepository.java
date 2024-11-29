package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.stereotype.Repository;

import com.industrial.pasantias.Model.Materia;

@Repository()


public interface MateriaRepository extends JpaRepository<Materia, Integer> {
}
