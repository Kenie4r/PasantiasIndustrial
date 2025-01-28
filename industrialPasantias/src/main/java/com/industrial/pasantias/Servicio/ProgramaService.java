package com.industrial.pasantias.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.industrial.pasantias.Model.Programa;
import com.industrial.pasantias.Model.ProgramaResumen;
import com.industrial.pasantias.Repository.ProgramaRepository;

@Service
public class ProgramaService {
    @Autowired
    private ProgramaRepository repository;

    public List<Programa> ObternerTodo() {
        return repository.findAll();
    }

    public Programa obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Programa> obtenerPorIdEmpresa(Integer idEmpresa) {
        return repository.findProgramasByIdEmpresa(idEmpresa);
    }

    public Programa guardar(Programa usuario) {
        return repository.save(usuario);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    public List<ProgramaResumen> obtenerProgramasEmpresa(Integer fechaIni) {
        return repository.findProgramasByMesAndTipoPrograma(fechaIni);
    }
}