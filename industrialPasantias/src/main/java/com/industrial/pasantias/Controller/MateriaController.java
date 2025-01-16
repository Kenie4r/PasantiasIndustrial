package com.industrial.pasantias.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.industrial.pasantias.Model.Carrera;
import com.industrial.pasantias.Model.Materia;

import org.springframework.web.bind.annotation.PostMapping;

import com.industrial.pasantias.Servicio.CarreraService;
import com.industrial.pasantias.Servicio.MateriaService;

@Controller
@RequestMapping("/materias")
public class MateriaController {
    private final MateriaService materiaService;

    @Autowired
    private CarreraService carreraService;

    public MateriaController(MateriaService materia) {
        this.materiaService = materia;
    }

    // Mostrar materias
    @GetMapping
    public String listarMaterias(Model model) {
        List<Materia> materias = materiaService.listar();
        model.addAttribute("materias", materias);
        return "materia/index";
    }

    // Mostrar form nueva materia
    @GetMapping("/nueva")
    public String nuevaMateriaForm(Model model) {
        List<Carrera> carrerasActivas = carreraService.obtenerCarrerasActivas();
        model.addAttribute("materia", new Materia());
        model.addAttribute("carreras", carrerasActivas);
        return "materia/crear_editar_materia";
    }

    // Guardar materia
    @PostMapping("/guardar")
    public String guardarMateria(@ModelAttribute Materia materia, RedirectAttributes redirectAttributes, Model model) {
        try {
            materiaService.guardar(materia);
            redirectAttributes.addFlashAttribute("mensaje", "La materia se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/materias";
        } catch (Exception e) {
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("mensaje", "Ocurrió un error al guardar la materia.");
            return "materia/crear_editar_materia";
        }
    }

    // Mostrar form editar materia
    @GetMapping("/editar/{id}")
    public String EditarMateriaForm(@PathVariable Integer id, Model model) {
        Materia materia = materiaService.obtenerPorId(id);
        if (materia == null) {
            return "redirect:/materias";
        }

        List<Carrera> carrerasActivas = carreraService.obtenerCarrerasActivas();
        model.addAttribute("materia", materia);
        model.addAttribute("carreras", carrerasActivas);
        return "materia/crear_editar_materia";
    }

    // Editar materia
    @PostMapping("/editar/{id}")
    public String editarMateria(@PathVariable Integer id, @ModelAttribute Materia materia,
            RedirectAttributes redirectAttributes, Model model) {
        try {
            Materia materiaExistente = materiaService.obtenerPorId(id);
            if (materia == null) {
                return "redirect:/materias";
            }
            System.out.println("materia:" + materia);
            materiaExistente.setNombre(materia.getNombre());
            materiaExistente.setCarrera(materia.getCarrera());
            materiaExistente.setEstado(materia.getEstado());
            materiaExistente.setHoras(materia.getHoras());

            materiaService.guardar(materiaExistente);
            redirectAttributes.addFlashAttribute("mensaje", "La materia se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/materias";
        } catch (Exception e) {
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("mensaje", "Ocurrió un error al actualizar la materia.");
            return "materia/crear_editar_materia";
        }
    }

    // Eliminar materia
    @GetMapping("/eliminar/{id}")
    public String eliminarMateria(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            materiaService.eliminarMateria(id);
            redirectAttributes.addFlashAttribute("mensaje", "La materia se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar la materia.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/materias";
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<List<Materia>> obtenerMateriasPorCarrera(@RequestParam("carreraId") Integer carreraId) {
        List<Materia> materias = materiaService.obtenerPorCarreraId(carreraId);
        return ResponseEntity.ok(materias);
    }

}
