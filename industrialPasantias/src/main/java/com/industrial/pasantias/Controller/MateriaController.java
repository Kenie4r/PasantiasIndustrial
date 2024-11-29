package com.industrial.pasantias.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.industrial.pasantias.Model.Materia;
import com.industrial.pasantias.Servicio.CarreraService; // Servicio para listar carreras
import com.industrial.pasantias.Servicio.MateriaService;

@Controller
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaService materiaService;
    private final CarreraService carreraService; // Servicio para gestionar carreras

    // Constructor para inyectar dependencias
    public MateriaController(MateriaService materiaService, CarreraService carreraService) {
        this.materiaService = materiaService;
        this.carreraService = carreraService;
    }

    // Listar materias
    @GetMapping
    public String listarMaterias(Model model) {
        model.addAttribute("materias", materiaService.listar()); // Lista de materias
        return "materia/index"; // Vista del listado
    }

    // Mostrar formulario para crear nueva materia
    @GetMapping("/nueva")
    public String nuevaMateriaForm(Model model) {
        model.addAttribute("materia", new Materia()); // Objeto vacío para creación
        model.addAttribute("carreras", carreraService.listarTodas()); // Lista de carreras
        return "materia/materiaForm"; // Vista del formulario
    }

    // Guardar materia (creación)
    @PostMapping("/guardar")
    public String guardarMateria(@ModelAttribute("materia") Materia materia) {
        materiaService.crear(materia); // Guardar nueva materia
        return "redirect:/materias"; // Redirigir al listado
    }

    // Mostrar formulario para editar materia
    @GetMapping("/editar/{id}")
    public String editarMateriaForm(@PathVariable("id") Integer id, Model model) {
        Materia materia = materiaService.obtenerPorId(id); // Obtener materia por ID
        model.addAttribute("materia", materia); // Cargar materia existente
        model.addAttribute("carreras", carreraService.listarTodas()); // Lista de carreras
        return "materia/materiaForm"; // Reutilizar la misma vista del formulario
    }

    // Guardar cambios en la materia (edición)
    @PostMapping("/editar/{id}")
    public String editarMateria(@PathVariable("id") Integer id, @ModelAttribute Materia materia) {
        materia.setIdMateria(id); // Asegurar que el ID esté presente
        materiaService.actualizarMateria(materia); // Actualizar materia existente
        return "redirect:/materias"; // Redirigir al listado
    }

    // Eliminar materia
    @GetMapping("/eliminar/{id}")
    public String eliminarMateria(@PathVariable Integer id) {
        materiaService.eliminarMateria(id); // Llamar al servicio para eliminar
        return "redirect:/materias"; // Redirigir al listado
    }
}
