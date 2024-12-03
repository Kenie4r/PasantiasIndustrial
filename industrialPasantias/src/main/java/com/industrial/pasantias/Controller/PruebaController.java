package com.industrial.pasantias.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.industrial.pasantias.Model.RolEntity;
import com.industrial.pasantias.Servicio.RolService;


@RestController
@RequestMapping("/prueba")
public class PruebaController {


    /* 
     * 
     *  @GetMapping("/home")
    public String Prueba(Model model){

        model.addAttribute("mensaje", "¡Hola desde el controlador!");
        return "prueba";
    }
     * 
    */

    @Autowired
    private RolService service;

    /** 
    @PostMapping("/crear")
    public RolEntity crearRol(@RequestBody RolDto body){

        Optional<RolEntity> rol = service.crearRol(body);

        if(rol.isPresent())
        {
            return rol.orElse(new RolEntity());
        }

        return null;
    }

    */

    /*
     * 
     *  @PostMapping("/modificar")
    public RolEntity modificarRol(@RequestBody RolDtoMod body){

        Optional<RolEntity> rol = service.modificarRol(body);

        if(rol.isPresent())
        {
            return rol.orElse(new RolEntity());
        }

        return null;
    }
   
     */
   
    @DeleteMapping("/eliminar/{id}")
    public RolEntity eliminarRol(@PathVariable int id){

        Optional<RolEntity> eliminarRol = service.eliminarRol(id);

        if(eliminarRol.isPresent())
        {
            return eliminarRol.orElse(new RolEntity());
        }

        return null;
    }

    
    @GetMapping("/datamod/{id}")
    public RolEntity obtenerDataMod(@PathVariable int id){

        Optional<RolEntity> dtaMod = service.obtenerDataModificar(id);

        if(dtaMod.isPresent())
        {
            return dtaMod.orElse(new RolEntity());
        }

        return null;
    }

}
