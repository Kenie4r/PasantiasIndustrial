package com.industrial.pasantias.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.Model.Pensum;
import com.industrial.pasantias.Repository.PensumRepository;

@Service
public class PensumService {

    @Autowired
    private PensumRepository pensumRepository;

    public List<Pensum> obtenerTodos() {
        return pensumRepository.findAll();
    }

    public Optional<Pensum> guardar(Pensum pensum) {

        try {

            Pensum pensumGuardado = pensumRepository.save(pensum);

            return Optional.of(pensumGuardado);

        } catch (Exception e) {
            return Optional.empty();
        }
    }



    public Optional<Pensum> editarPensum(Pensum pensum) {

        try {

            Pensum pensumGuardado = pensumRepository.save(pensum);

            return Optional.of(pensumGuardado);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Pensum> obtenerPorId(int id) {

        try {

            Pensum pensumDatos = pensumRepository.findById(id).get();

            return Optional.of(pensumDatos);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Pensum> eliminar(int id) {

        try {

            Pensum pensumAEliminar = pensumRepository.findById(id).get();

            pensumRepository.delete(pensumAEliminar);

            return Optional.of(pensumAEliminar);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
