package com.industrial.pasantias.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.Model.Pasantia;
import com.industrial.pasantias.Repository.PasantiaRepository;

@Service
public class PasantiaService {
    @Autowired
    private PasantiaRepository pasantiaRepository;

    public List<Pasantia> obtenerTodos() {
        return pasantiaRepository.findAll();
    }

    public Pasantia guardar(Pasantia pasantia) {
        return pasantiaRepository.save(pasantia);
    }
}
