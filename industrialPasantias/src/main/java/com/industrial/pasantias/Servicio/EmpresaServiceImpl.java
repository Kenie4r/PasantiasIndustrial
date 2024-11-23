package com.industrial.pasantias.Servicio;

import org.springframework.stereotype.Service;
import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.DAO.EmpresaRepository;

import java.util.List;

@Service
public class EmpresaServiceImpl implements EmpresaService {
    private final EmpresaRepository empresaRepository;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public List<Empresa> listarTodas() {
        return empresaRepository.findAll(); // Obtiene todas las empresas desde la base de datos
    }
}
