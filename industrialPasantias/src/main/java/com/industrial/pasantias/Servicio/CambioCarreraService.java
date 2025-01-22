package com.industrial.pasantias.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.industrial.pasantias.Repository.CambioCarreraRepository;

@Service
@Transactional
public class CambioCarreraService {
    @Autowired
    private CambioCarreraRepository cambioCarreraRepository;

    public void eliminar(String carnet) {        
        cambioCarreraRepository.deleteByCarnet(carnet);
    }

}
