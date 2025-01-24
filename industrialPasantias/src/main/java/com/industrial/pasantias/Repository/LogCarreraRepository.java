package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;

import com.industrial.pasantias.Model.LogCarreras;

public interface LogCarreraRepository extends JpaRepository<LogCarreras, Integer> {

}
