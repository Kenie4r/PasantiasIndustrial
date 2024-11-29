package com.industrial.pasantias.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.industrial.pasantias.Model.Parametro;
import com.industrial.pasantias.Repository.ParametroRepository;

@Service
public class ParametroService {
    @Autowired
    private ParametroRepository parametroReposiroty;

    public List<Parametro> obtenerTodos() {
        return parametroReposiroty.findAll();
    }

    public Parametro obtenerPorId(Integer id) {
        return parametroReposiroty.findById(id).orElse(null);
    }

    public Parametro guardar(Parametro usuario) {
        return parametroReposiroty.save(usuario);
    }

    public void eliminar(Integer id) {
        parametroReposiroty.deleteById(id);
    }
}
