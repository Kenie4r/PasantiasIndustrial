package com.industrial.pasantias.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.Model.Rubro;
import com.industrial.pasantias.Repository.RubroRepository;

@Service
public class RubroService {
    @Autowired
    private RubroRepository rubroRepository;

    public List<Rubro> obtenerTodos() {
        return rubroRepository.findAll();
    }

    public Rubro obtenerPorId(Integer id) {
        return rubroRepository.findById(id).orElse(null);
    }

    public Rubro guardar(Rubro rubro) {
        return rubroRepository.save(rubro);
    }

    public void eliminar(Integer id) {
        rubroRepository.deleteById(id);
    }
}
