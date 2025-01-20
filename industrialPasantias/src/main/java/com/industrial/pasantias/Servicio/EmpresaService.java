package com.industrial.pasantias.Servicio;

import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Repository.EmpresaRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa guardar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public void eliminarEmpresa(Integer idEmpresa) {
        empresaRepository.deleteById(idEmpresa);
    }

    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    public Empresa obtenerPorId(Integer id) {
        return empresaRepository.findById(id).orElse(null);
    }
}
