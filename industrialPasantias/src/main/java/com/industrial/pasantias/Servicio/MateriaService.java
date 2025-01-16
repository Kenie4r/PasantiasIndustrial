package com.industrial.pasantias.Servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.industrial.pasantias.Model.Materia;
import com.industrial.pasantias.Repository.MateriaRepository;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    public List<Materia> listar() {
        return materiaRepository.findAll();
    }

    public Materia obtenerPorId(Integer id) {
        return materiaRepository.findById(id).orElse(null);
    }

    public Materia guardar(Materia nMateriaEntity) {
        return materiaRepository.save(nMateriaEntity);
    }

    public void eliminarMateria(Integer idMateria) {
        materiaRepository.deleteById(idMateria);
    }
    public List<Materia> obtenerPorCarreraId(Integer id) {
        return materiaRepository.findMateriasByIdCarrera(id);
    }
}
