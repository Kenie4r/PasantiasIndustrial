package com.industrial.pasantias.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.industrial.pasantias.Model.RolEntity;
import com.industrial.pasantias.Servicio.RolService;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService service;

    @GetMapping("/obtenerTodos")
    public String listarTodos(Model model){

        Optional<List<RolEntity>> roles = service.obtenerTodos();

        if(roles.isPresent()){
            model.addAttribute("roles", roles.orElse(new ArrayList<>()));
        }

        if(!roles.isPresent()){
            model.addAttribute("roles", new ArrayList<>());
        }
        
        return "rol/listado_roles";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioRol(Model model) {
        model.addAttribute("rol", new RolEntity());

        return "rol/creareditarrol";
    }

    @PostMapping("/crear")
    public String guardarRol(@ModelAttribute RolEntity rol, RedirectAttributes redirectAttributes) {
        try {
      
            service.crearRol(rol);
            redirectAttributes.addFlashAttribute("mensaje", "El rol se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/roles/obtenerTodos";
    }

    @PostMapping("/editar/{id}")
    public String editarRol(@PathVariable int id, RedirectAttributes redirectAttributes, @ModelAttribute RolEntity rol) {
        try {
      
            Optional<RolEntity> optional = service.obtenerDataModificar(id);

            if(optional.isPresent()){
                
                RolEntity rolExistente = optional.orElse(new RolEntity());

                rolExistente.setFECHA_CREA(rolExistente.getFECHA_CREA());
                rolExistente.setDESCRIPCION(rol.getDESCRIPCION());
                rolExistente.setESTADO(rol.getESTADO());

                redirectAttributes.addFlashAttribute("mensaje", "El rol se editó correctamente.");
                redirectAttributes.addFlashAttribute("tipoMensaje", "success");
                service.modificarRol(rolExistente);
                return "redirect:/roles/obtenerTodos";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/roles/obtenerTodos";
    }

    @GetMapping("/editar/{id}")
    public String editarRolForm(@PathVariable int id, Model model) {
        try {
      
            Optional<RolEntity> rol = service.obtenerDataModificar(id);

            if(rol.isPresent()){

                model.addAttribute("rol", rol.get());
            }
            
            return "rol/creareditarrol";
        } catch (Exception e) {
            return "redirect:/roles/obtenerTodos";    
        }
        
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarParametro(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.eliminarRol(id);
            redirectAttributes.addFlashAttribute("mensaje", "El rol se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/roles/obtenerTodos";
    }
    
}
