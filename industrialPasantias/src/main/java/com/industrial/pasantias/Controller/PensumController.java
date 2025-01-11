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

import com.industrial.pasantias.Model.Pensum;
import com.industrial.pasantias.Servicio.PensumService;

@Controller
@RequestMapping("/pensums")
public class PensumController {

    @Autowired
    private PensumService service;

    @GetMapping("/obtenerTodos")
    public String listarTodos(Model model){

        Optional<List<Pensum>> pensums = service.obtenerTodos();

        if(pensums.isPresent()){
            model.addAttribute("pensum", pensums.orElse(new ArrayList<>()));
        }

        if(!pensums.isPresent()){
            model.addAttribute("pensum", new ArrayList<>());
        }
        
        return "pensum/mostrartodos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioPensum(Model model) {
        model.addAttribute("pensum", new Pensum());

        return "pensum/creareditarpensum";
    }

    @PostMapping("/crear")
    public String guardarPensum(@ModelAttribute Pensum pensum, RedirectAttributes redirectAttributes) {
        try {
      
            service.guardar(pensum);
            redirectAttributes.addFlashAttribute("mensaje", "El pensum se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/pensums/obtenerTodos";
    }

    @PostMapping("/editar/{id}")
    public String editarPensum(@PathVariable int id, RedirectAttributes redirectAttributes, @ModelAttribute Pensum nuevoPensum) {
        try {
      
            Optional<Pensum> optional = service.obtenerPorId(id);

            if(optional.isPresent()){
                
                Pensum pensumExistente = optional.orElse(new Pensum());

               
                pensumExistente.setNOMBRE(nuevoPensum.getNOMBRE());

                redirectAttributes.addFlashAttribute("mensaje", "El pensum se editó correctamente.");
                redirectAttributes.addFlashAttribute("tipoMensaje", "success");
                service.editarPensum(pensumExistente);
                return "redirect:/pensums/obtenerTodos";

            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/pensums/obtenerTodos";
    }

    @GetMapping("/editar/{id}")
    public String editarPensumForm(@PathVariable int id, Model model) {
        try {
      
            Optional<Pensum> pensum = service.obtenerPorId(id);

            if(pensum.isPresent()){

                model.addAttribute("pensum", pensum.get());
            }
            
            return "pensum/creareditarpensum";
        } catch (Exception e) {
            return "redirect:/pensums/obtenerTodos";    
        }
        
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPensum(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {

            service.eliminar(id);

            redirectAttributes.addFlashAttribute("mensaje", "El pensum se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el pensum.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/pensums/obtenerTodos";
    }
}
