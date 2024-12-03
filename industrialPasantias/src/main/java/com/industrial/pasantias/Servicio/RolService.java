package com.industrial.pasantias.Servicio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.industrial.pasantias.Model.RolEntity;
import com.industrial.pasantias.Repository.RolRepository;
import com.industrial.pasantias.dto.RolDto;
import com.industrial.pasantias.dto.RolDtoMod;

@Service
public class RolService {

    @Autowired
    private RolRepository repository;

    public Optional<RolEntity> eliminarRol(int id) {

        try {

            Optional<RolEntity> entidad = repository.findById(id);

            if (entidad.isPresent()) {
                repository.deleteById(id);

                return Optional.of(entidad.get());
            }

            return Optional.empty();

        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public Optional<List<RolEntity>> obtenerTodos(){

        try {

            List<RolEntity> roles = repository.findAll();

            if(roles == null || roles.isEmpty()){
                return Optional.empty();
            }

            return Optional.of(roles);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<RolEntity> obtenerDataModificar(int id) {

        try {

            Optional<RolEntity> entidad = repository.findById(id);

            if (entidad.isPresent()) {
                return Optional.of(entidad.get());
            }

            return Optional.empty();

        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public Optional<RolEntity> modificarRol(RolEntity dto) {

        RolEntity entity = new RolEntity();

        entity.setID_ROL(dto.getID_ROL());
        entity.setDESCRIPCION(dto.getDESCRIPCION());
        entity.setESTADO(dto.getESTADO());
        entity.setFECHA_MOD(new Date(System.currentTimeMillis()));
        entity.setFECHA_CREA(dto.getFECHA_CREA());


        try {

            repository.save(entity);

            return Optional.of(entity);
        } catch (Exception e) {

            return Optional.empty();
        }

    }

    public Optional<RolEntity> crearRol(@ModelAttribute RolEntity dto) {

        RolEntity entity = new RolEntity();

        entity.setDESCRIPCION(dto.getDESCRIPCION());
        entity.setESTADO(dto.getESTADO());
        entity.setFECHA_CREA(new Date(System.currentTimeMillis()));

        try {

            repository.save(entity);

            return Optional.of(entity);
        } catch (Exception e) {

            return Optional.empty();
        }

    }

    public List<RolEntity> obtenerRolesActivos() {
        return repository.encontrarRolesActivos("A"); 
    }
}
