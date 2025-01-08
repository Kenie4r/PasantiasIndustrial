package com.industrial.pasantias.Servicio;


import java.sql.Date;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.industrial.pasantias.Model.EstudianteEntity;
import com.industrial.pasantias.Repository.EstudianteRepository;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository repository;

    public Optional<EstudianteEntity> eliminarEstudiante(String carnet) {

        try {

            Optional<EstudianteEntity> entidad = repository.findByCarnet(carnet);

            if (entidad.isPresent()) {
                repository.delete(entidad.get());

                return Optional.of(entidad.get());
            }

            return Optional.empty();

        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public Optional<List<EstudianteEntity>> obtenerTodos() {

        try {

            List<EstudianteEntity> roles = repository.findAll();

            if (roles == null || roles.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(roles);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<EstudianteEntity> obtenerDataModificar(String carnet) {
        try {
            Optional<EstudianteEntity> entidad = repository.findByCarnet(carnet);
            if (entidad.isPresent()) {
                return Optional.of(entidad.get());
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public Optional<EstudianteEntity> modificarEstudiante(EstudianteEntity dto) {

        EstudianteEntity entity = new EstudianteEntity();

        entity.setCarnet(dto.getCarnet());
        entity.setAPELLIDOS(dto.getAPELLIDOS());
        entity.setCORREO(dto.getCORREO());
        entity.setTELEFONO(dto.getTELEFONO());
        entity.setTELEFONO2(dto.getTELEFONO2());
        entity.setHOJA_DE_VIDA(dto.getHOJA_DE_VIDA());
        entity.setFOTO_URL(dto.getFOTO_URL());
        entity.setNOMBRES(dto.getNOMBRES());
        entity.setCarrera(dto.getCarrera());


        entity.setFECHA_MOD(new Date(System.currentTimeMillis()));
        entity.setFECHA_CREA(dto.getFECHA_CREA());

        try {

            repository.save(entity);

            return Optional.of(entity);
        } catch (Exception e) {

            return Optional.empty();
        }

    }

    public Optional<EstudianteEntity> crearEstudiante(@ModelAttribute EstudianteEntity dto) {

        Optional<EstudianteEntity> estudianteEntity = repository.findByCarnet(dto.getCarnet());

        if (estudianteEntity.isPresent()) {
            return Optional.empty();
        }

        // EstudianteEntity entity = new EstudianteEntity();

        try {

            repository.save(dto);

            return Optional.of(dto);
        } catch (Exception e) {

            return Optional.empty();
        }

    }

    public List<EstudianteEntity> obtenerPorIdCarrera(int id) {
        return repository.encontrarPorCarrera(id);
    }
}
