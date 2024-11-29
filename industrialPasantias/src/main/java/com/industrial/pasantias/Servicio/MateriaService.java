package com.industrial.pasantias.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.DAO.EmpresaRepository;
import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Model.Materia;

import com.industrial.pasantias.Repository.MateriaRepository;

@Service
public class MateriaService {
   @Autowired
    private MateriaRepository materiaRepository ;
    public List<Materia> listar(){
        return materiaRepository.findAll();
    }
    public Materia crear(Materia nMateriaEntity){
        return materiaRepository.save(nMateriaEntity);
    }
    public void eliminarMateria(int idMateria){
        materiaRepository.deleteById(idMateria);
    }
     public Materia actualizarEmpresa(Materia materia) {
        // Verificar si la empresa existe en la base de datos
        Optional<Materia> empresaExistente = materiaRepository.findById(materia.getIdMateria());
        if (empresaExistente.isPresent()) {
            Materia actual = empresaExistente.get();

            // Actualizar los campos de la empresa
            actual.setNombre(materia.getNombre());
            actual.setEstado(materia.getEstado());
          
            actual.setHoras(materia.getHoras());
            actual.setIdCarrera(materia.getIdMateria());;

            // Guardar los cambios
            return materiaRepository.save(actual);
        } else {
            throw new RuntimeException("La empresa con ID " + materia.getIdMateria() + " no existe.");
        }
    }
}
