package com.industrial.pasantias.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.Model.Programa;
import com.industrial.pasantias.Model.Pasantia;
import com.industrial.pasantias.Model.PasantiaPrograma;
import com.industrial.pasantias.Model.PK.PasantiaProgramaPK;
import com.industrial.pasantias.Repository.PasantiaProgramaRepository;

@Service
public class PasantiaProgramaService {
    @Autowired
    private PasantiaProgramaRepository pasantiaProgramaRepository;

    @Autowired
    private PasantiaService pasantiaService;

    @Autowired
    private ProgramaService programaService;

    public List<PasantiaPrograma> obtenerTodos() {
        return pasantiaProgramaRepository.findAll();
    }

    public List<PasantiaPrograma> obtenerPorIdPasantia(int id) {
        return pasantiaProgramaRepository.encontrarPorIdPasantia(id);
    }

    public PasantiaPrograma guardar(PasantiaPrograma pasantiaPrograma) {
        return pasantiaProgramaRepository.save(pasantiaPrograma);
    }

    public Optional<PasantiaPrograma> obtenerPorIdPasantiaPrograma(int idPasantia, int idPrograma) {
        Pasantia pasantia = pasantiaService.obtenerPorIdPasantia(idPasantia);
        Programa programa = programaService.obtenerPorId(idPrograma);
        PasantiaProgramaPK id = new PasantiaProgramaPK(pasantia, programa);
        return pasantiaProgramaRepository.findById(id);
    }

    public Optional<PasantiaPrograma> obtenerPorIdPasantiaPrograma(Pasantia pasantia, Programa programa) {
        PasantiaProgramaPK id = new PasantiaProgramaPK(pasantia, programa);
        return pasantiaProgramaRepository.findById(id);
    }

    public Optional<PasantiaPrograma> obtenerPorIdPasantiaPrograma(PasantiaProgramaPK id) {
        return pasantiaProgramaRepository.findById(id);
    }

    public void eliminar(PasantiaPrograma pasantiaPrograma) {
        pasantiaProgramaRepository.delete(pasantiaPrograma);
    }
}
