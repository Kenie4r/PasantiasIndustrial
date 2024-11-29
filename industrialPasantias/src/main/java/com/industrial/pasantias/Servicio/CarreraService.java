package com.industrial.pasantias.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.industrial.pasantias.Model.Carrera;
import com.industrial.pasantias.Repository.CarreraRepository;

@Service
public class CarreraService {
    @Autowired
    private CarreraRepository carreraRepository;

    public List<Carrera> obtenerTodos() {
        return carreraRepository.findAll();
    }

    public Carrera obtenerPorId(Integer id) {
        return carreraRepository.findById(id).orElse(null);
    }

    public Carrera guardar(Carrera usuario) {
        return carreraRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        carreraRepository.deleteById(id);
    }
}
