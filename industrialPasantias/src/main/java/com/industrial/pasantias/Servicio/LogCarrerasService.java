package com.industrial.pasantias.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.Model.LogCarreras;
import com.industrial.pasantias.Repository.LogCarreraRepository;

@Service
public class LogCarrerasService {
   @Autowired
   private LogCarreraRepository LogCarreraRepository;

   public List<LogCarreras> listaCambios() {
      return LogCarreraRepository.findAll();
   }
}
