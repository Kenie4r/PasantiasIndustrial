package com.industrial.pasantias.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.DAO.EmpresaRepository;
import com.industrial.pasantias.Model.Empresa;

@Service
public class EmpresaService {
    @Autowired
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Empresa save(Empresa empresa) {
        empresa.setFechaMod(java.time.LocalDateTime.now());
        if (empresa.getIdEmpresa() == null) {
            empresa.setFechaCrea(java.time.LocalDateTime.now());
        }
        return empresaRepository.save(empresa);
    }
    public Empresa findById(Long id) {
       Optional<Empresa> empresa=empresaRepository.findById(id);
        if (empresa.isEmpty()) {
            throw new RuntimeException("Empresa con ID " + id + " no encontrada.");
        }
        return empresa.get();
    }
    
    public void deleteById(Long id){
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa con id no encontrada");
        }
        empresaRepository.deleteById(id);
    }

}
