package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.industrial.pasantias.Model.Pensum;

@Repository
public interface PensumRepository extends JpaRepository<Pensum, Integer > {


}
