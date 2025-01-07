package com.industrial.pasantias.Servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.Model.Pasantia;
import com.industrial.pasantias.Repository.PasantiaRepository;
import com.industrial.pasantias.ViewModel.SelectListItem;

@Service
public class PasantiaService {
    @Autowired
    private PasantiaRepository pasantiaRepository;

    public List<Pasantia> obtenerTodos() {
        return pasantiaRepository.findAll();
    }

    public Pasantia obtenerPorIdPasantia(int id) {
        return pasantiaRepository.findByIdPasantia(id);
    }

    public Pasantia guardar(Pasantia pasantia) {
        return pasantiaRepository.save(pasantia);
    }

    public List<SelectListItem> obtenerEstadosPasantia(){
        List<SelectListItem> estados = new ArrayList<>();
        SelectListItem estado1 = new SelectListItem("E", "En Proceso");
        estados.add(estado1);
        estado1 = new SelectListItem("F", "Finalizado");
        estados.add(estado1);
        return estados;
    }

    public Integer ObtenerCorrelativoParaNuevaPasantia(int idCarrera){
        return (pasantiaRepository.obtenerUltimoCorrelativoPorCarrera(idCarrera) + 1);
    }
}
