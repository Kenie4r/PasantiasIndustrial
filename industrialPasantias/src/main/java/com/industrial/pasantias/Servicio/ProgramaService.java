package com.industrial.pasantias.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.industrial.pasantias.Model.EmpresaPrograma;
import com.industrial.pasantias.Repository.ProgramaRepository;

@Service
public class ProgramaService {
    @Autowired
    private ProgramaRepository repository;

    public List<EmpresaPrograma> ObternerTodo() {
        return repository.findAll();
    }

    public EmpresaPrograma obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<EmpresaPrograma> obtenerPorIdEmpresa(Integer idEmpresa) {
        return repository.findProgramasByIdEmpresa(idEmpresa);
    }

    public EmpresaPrograma guardar(EmpresaPrograma usuario) {
        return repository.save(usuario);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}