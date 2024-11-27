package com.industrial.pasantias.Servicio;

import com.industrial.pasantias.DAO.EmpresaRepository;
import com.industrial.pasantias.Model.Empresa;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EmpresaService {
        @Autowired
    private EmpresaRepository empresaRepository;


 
    public Empresa crearEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
    public Empresa actualizarEmpresa(Empresa empresa) {
        // Verificar si la empresa existe en la base de datos
        Optional<Empresa> empresaExistente = empresaRepository.findById(empresa.getIdEmpresa());
        if (empresaExistente.isPresent()) {
            Empresa actual = empresaExistente.get();

            // Actualizar los campos de la empresa
            actual.setNombre(empresa.getNombre());
            actual.setEstado(empresa.getEstado());
            actual.setIdRubro(empresa.getIdRubro());
            actual.setUbicacion(empresa.getUbicacion());
            actual.setTelefono(empresa.getTelefono());
            actual.setSitioWeb(empresa.getSitioWeb());

            // Guardar los cambios
            return empresaRepository.save(actual);
        } else {
            throw new RuntimeException("La empresa con ID " + empresa.getIdEmpresa() + " no existe.");
        }
    }
    public void eliminarEmpresa(int idEmpresa) {
        empresaRepository.deleteById(idEmpresa); // Elimina la empresa del repositorio
    }
     public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }
}

