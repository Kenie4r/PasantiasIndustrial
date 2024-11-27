package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.industrial.pasantias.Model.RolEntity;

@Repository()
public interface RolRepository extends JpaRepository<RolEntity, Integer>{

}
