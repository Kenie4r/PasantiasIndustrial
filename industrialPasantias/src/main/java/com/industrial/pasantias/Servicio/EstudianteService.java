package com.industrial.pasantias.Servicio;

import java.sql.Date;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.industrial.pasantias.Controller.EstudianteController;
import com.industrial.pasantias.Model.CambioDeCarrera;
import com.industrial.pasantias.Model.EstudianteEntity;
import com.industrial.pasantias.Repository.CambioCarreraRepository;
import com.industrial.pasantias.Repository.EstudianteRepository;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CambioCarreraRepository logCambioDeCarreraRepository;

    private static Logger logger = LoggerFactory.getLogger(EstudianteController.class);

    public EstudianteEntity obtenerPorCarnet(String carnet) {
        return estudianteRepository.encontrarPorCarnet(carnet);
    }

    public void eliminar(String carnet) {
        estudianteRepository.deleteById(carnet);
    }

    public Optional<List<EstudianteEntity>> obtenerTodos() {

        try {

            List<EstudianteEntity> roles = estudianteRepository.findAll();

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
            Optional<EstudianteEntity> entidad = estudianteRepository.findByCarnet(carnet);
            if (entidad.isPresent()) {
                return Optional.of(entidad.get());
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public Optional<CambioDeCarrera> insertarCambioCarrera(CambioDeCarrera carreraNueva) {
        try {

            CambioDeCarrera cambio = logCambioDeCarreraRepository.save(carreraNueva);

            return Optional.ofNullable(cambio);

        } catch (Exception e) {
            logger.error("Error al ingresar el cambio / inicio de carrera {}", e.getMessage());
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
            estudianteRepository.save(entity);
            return Optional.of(entity);
        } catch (Exception e) {

            return Optional.empty();
        }

    }

    public Optional<EstudianteEntity> crearEstudiante(@ModelAttribute EstudianteEntity dto) {

        Optional<EstudianteEntity> estudianteEntity = estudianteRepository.findByCarnet(dto.getCarnet());

        if (estudianteEntity.isPresent()) {
            return Optional.empty();
        }

        // EstudianteEntity entity = new EstudianteEntity();
        try {
            estudianteRepository.save(dto);

            return Optional.of(dto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<EstudianteEntity> obtenerPorIdCarrera(int id) {
        return estudianteRepository.encontrarPorCarrera(id);
    }

}
