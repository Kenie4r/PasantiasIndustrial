package com.industrial.pasantias.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.industrial.pasantias.Model.Pensum;
import com.industrial.pasantias.Repository.PensumRepository;

@Service
public class PensumService {

    private PensumRepository repository;

    public Optional<Pensum> guardar(Pensum pensum) {

        try {

            Pensum pensumGuardado = repository.save(pensum);

            return Optional.of(pensumGuardado);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    
    public Optional<List<Pensum>> obtenerTodos() {

        try {

            List<Pensum> lista = repository.findAll();

            return Optional.ofNullable(lista);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Pensum> editarPensum(Pensum pensum) {

        try {

            Pensum pensumGuardado = repository.save(pensum);

            return Optional.of(pensumGuardado);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Pensum> obtenerPorId(int id) {

        try {

            Pensum pensumDatos = repository.findById(id).get();

            return Optional.of(pensumDatos);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Pensum> eliminar(int id) {

        try {

            Pensum pensumAEliminar = repository.findById(id).get();

            repository.delete(pensumAEliminar);

            return Optional.of(pensumAEliminar);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
