package com.industrial.pasantias.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.Repository.PasantiaProgramaRepository;

@Service
public class PasantiaProgramaService {
    @Autowired
    private PasantiaProgramaRepository pasantiaProgramaRepository;
}
