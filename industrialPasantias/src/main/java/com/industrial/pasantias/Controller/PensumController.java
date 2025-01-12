package com.industrial.pasantias.Controller;

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
    private PensumService pensumService;

    // Obtener todos los pensum
    @GetMapping("/obtenerTodos")
    public String listarTodos(Model model){
        List<Pensum> pensums = pensumService.obtenerTodos();
        model.addAttribute("pensums", pensums);
        return "pensum/mostrartodos";
    }

    // Mostrar formulario crear
    @GetMapping("/nuevo")
    public String mostrarFormularioPensum(Model model) {
        model.addAttribute("pensum", new Pensum());
        return "pensum/creareditarpensum";
    }

    // Guardar pensum
    @PostMapping("/crear")
    public String guardarPensum(@ModelAttribute Pensum pensum, RedirectAttributes redirectAttributes) {
        try {      
            pensumService.guardar(pensum);
            redirectAttributes.addFlashAttribute("mensaje", "El pénsum se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el pénsum.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/pensums/obtenerTodos";
    }

    // Editar pensum
    @PostMapping("/editar/{id}")
    public String editarPensum(@PathVariable int id, RedirectAttributes redirectAttributes, @ModelAttribute Pensum nuevoPensum) {
        try {
      
            Optional<Pensum> optional = pensumService.obtenerPorId(id);

            if(optional.isPresent()){
                
                Pensum pensumExistente = optional.orElse(new Pensum());

               
                pensumExistente.setNOMBRE(nuevoPensum.getNOMBRE());

                redirectAttributes.addFlashAttribute("mensaje", "El pénsum se editó correctamente.");
                redirectAttributes.addFlashAttribute("tipoMensaje", "success");
                pensumService.editarPensum(pensumExistente);
                return "redirect:/pensums/obtenerTodos";

            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el pénsum.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/pensums/obtenerTodos";
    }

    // Mostrar formulario editar
    @GetMapping("/editar/{id}")
    public String editarPensumForm(@PathVariable int id, Model model) {
        try {
      
            Optional<Pensum> pensum = pensumService.obtenerPorId(id);

            if(pensum.isPresent()){

                model.addAttribute("pensum", pensum.get());
            }
            
            return "pensum/creareditarpensum";
        } catch (Exception e) {
            return "redirect:/pensums/obtenerTodos";    
        }
        
    }

    // Eliminar pensum
    @GetMapping("/eliminar/{id}")
    public String eliminarPensum(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {

            pensumService.eliminar(id);

            redirectAttributes.addFlashAttribute("mensaje", "El pénsum se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el pénsum.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/pensums/obtenerTodos";
    }
}
