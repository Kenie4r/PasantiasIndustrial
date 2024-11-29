package com.industrial.pasantias.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.industrial.pasantias.Model.Carrera;
import com.industrial.pasantias.Servicio.CarreraService;

@Controller
public class CarrreraController {

    @Autowired
    private CarreraService carreraService;

    // Listar carreras
    @GetMapping("/carreras")
    public String listarCarreras(Model model) {
        List<Carrera> carreras = carreraService.obtenerTodos();
        model.addAttribute("carreras", carreras);
        return "carreras/listado_carreras";
    }

    // Mostrar formulario para crear una nueva carrera
    @GetMapping("/carreras/nuevo")
    public String mostrarFormularioNuevaCarrera(Model model) {
        model.addAttribute("carrera", new Carrera());
        return "carreras/crear_editar_carrera";
    }

    // Guardar carrera (Crear o Editar)
    @PostMapping("/carreras")
    public String guardarCarrera(@ModelAttribute Carrera carrera, RedirectAttributes redirectAttributes) {
        try {
            if (carrera.getIdCarrera() == null) {
                carrera.setFechaCre(LocalDateTime.now());
                carrera.setEstado("A");
                carrera.setUsuCrea("USUARIO");
            } else {
                carrera.setFechaMod(LocalDateTime.now());
                carrera.setUsuMod("USUARIO");
            }
            carreraService.guardar(carrera);
            redirectAttributes.addFlashAttribute("mensaje", "La carrera se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar la carrera.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/carreras";
    }

    // Mostrar formulario para editar un carrera
    @GetMapping("/carreras/editar/{id}")
    public String mostrarFormularioModificarCarrera(@PathVariable Integer id, Model model) {
        Carrera carrera = carreraService.obtenerPorId(id);
        if (carrera == null) {
            return "redirect:/carreras";
        }
        model.addAttribute("carrera", carrera);
        return "carreras/crear_editar_carrera";
    }

    // Actualizar carrera
    @PostMapping("/carreras/editar/{id}")
    public String actualizarCarrera(@PathVariable Integer id, @ModelAttribute Carrera carrera,
            RedirectAttributes redirectAttributes) {
        try {
            Carrera carreraExistente = carreraService.obtenerPorId(id);
            if (carrera == null) {
                return "redirect:/carreras";
            }

            carreraExistente.setDescripcion(carrera.getDescripcion());
            carreraExistente.setFechaMod(carrera.getFechaMod());
            carreraExistente.setUsuMod(carrera.getUsuMod());
            carreraExistente.setCodCarrera(carrera.getCodCarrera());

            carreraService.guardar(carreraExistente);
            redirectAttributes.addFlashAttribute("mensaje", "La carrera se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al actualizar la carrera.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/carreras";
    }

    // Eliminar un carrera
    @GetMapping("/carreras/eliminar/{id}")
    public String eliminarCarrera(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            carreraService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "La carrera se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar la carrera.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/carreras";
    }

    // Método para cambiar el estado del carrera a inactivo
    @PostMapping("/carreras/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Carrera carrera = carreraService.obtenerPorId(id);

            if (carrera != null) {
                if ("A".equals(carrera.getEstado())) {
                    carrera.setEstado("I");
                } else {
                    carrera.setEstado("A");
                }
                carreraService.guardar(carrera);
            }
            redirectAttributes.addFlashAttribute("mensaje", "La estado de la carrera se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al actualizar el estado de la carrera.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }

        return "redirect:/carreras";
    }
}
