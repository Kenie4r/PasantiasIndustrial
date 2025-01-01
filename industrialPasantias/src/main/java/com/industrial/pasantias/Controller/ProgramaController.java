package com.industrial.pasantias.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Model.EmpresaPrograma;
import com.industrial.pasantias.Model.Materia;
import com.industrial.pasantias.Servicio.EmpresaService;
import com.industrial.pasantias.Servicio.MateriaService;
import com.industrial.pasantias.Servicio.ProgramaService;

@Controller
@RequestMapping("/empresaPrograma")
public class ProgramaController {
    private final ProgramaService programaService;
    private final EmpresaService empresaService;
    private final MateriaService materiaService;

    public ProgramaController(ProgramaService progama, EmpresaService empresaService, MateriaService materiaService) {
        this.programaService = progama;
        this.empresaService = empresaService;
        this.materiaService = materiaService;
    }

    // Mostrar programas
    @GetMapping(path = { "", "/" })
    public String listarProgramas(Model model) {
        List<EmpresaPrograma> programas = programaService.ObternerTodo();
        model.addAttribute("programas", programas);
        return "empresaPrograma/index";
    }

    // Mostrar programa por id
    @GetMapping("/{id}")
    public String listarPrograma(@PathVariable Integer id, Model model) {
        List<EmpresaPrograma> programas = programaService.obtenerPorIdEmpresa(id);
        model.addAttribute("programas", programas);
        return "empresaPrograma/index";
    }

    // Mostrar form nuevo programa
    @GetMapping("/nuevo")
    public String FormGuardar(Model model) {
        List<Empresa> empresas = empresaService.listarEmpresas();
        List<Materia> materias = materiaService.listar();

        model.addAttribute("empresas", empresas);
        model.addAttribute("materia", materias);
        model.addAttribute("programa", new EmpresaPrograma());
        return "empresaPrograma/crear_editar_programa";
    }

    // Maneja la solicitud POST para guardar el nuevo programa
    @PostMapping("/guardar")
    public String guardarPrograma(@ModelAttribute("programa") EmpresaPrograma programa, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            // Guarda el nuevo programa en la base de datos
            programaService.guardar(programa);
            redirectAttributes.addFlashAttribute("mensaje", "La carrera se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar la carrera.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }

        return "redirect:/empresaPrograma";
    }

    // Mostrar formulario para editar el programa
    @GetMapping("/editar/{id}")
    public String mostrarFormularioModificarPrograma(@PathVariable Integer id,
            Model model) {

        EmpresaPrograma empresaPrograma = programaService.obtenerPorId(id);
        if (empresaPrograma == null) {
            return "redirect:/empresaPrograma";
        }
        List<Empresa> empresas = empresaService.listarEmpresas();
        List<Materia> materias = materiaService.listar();

        model.addAttribute("empresas", empresas);
        model.addAttribute("materia", materias);
        model.addAttribute("programa", empresaPrograma);
        System.out.println("programa: " + empresaPrograma);
        return "empresaPrograma/crear_editar_programa";
    }

    // Actualizar empresaPrograma

    @PostMapping("/editar/{id}")
    public String actualizarPrograma(@PathVariable Integer id, @ModelAttribute EmpresaPrograma empresaPrograma,
            RedirectAttributes redirectAttributes) {
        try {
            EmpresaPrograma programaExistente = programaService.obtenerPorId(id);
            if (empresaPrograma == null) {
                return "redirect:/empresaPrograma";
            }

            programaExistente.setNombrePrograma(empresaPrograma.getNombrePrograma());
            programaExistente.setTipoPrograma(empresaPrograma.getTipoPrograma());
            programaExistente.setFechaIni(empresaPrograma.getFechaIni());
            programaExistente.setFechaFi(empresaPrograma.getFechaFi());
            programaExistente.setEmpresa(empresaPrograma.getEmpresa());
            programaExistente.setMateria(empresaPrograma.getMateria());
            programaExistente.setEstado(empresaPrograma.getEstado());
            programaExistente.setNombreResponsable(empresaPrograma.getNombreResponsable());
            programaExistente.setAreaRealizacion(empresaPrograma.getAreaRealizacion());
            programaExistente.setOtrosDetalles(empresaPrograma.getOtrosDetalles());
            programaExistente.setObservaciones(empresaPrograma.getObservaciones());

            programaService.guardar(programaExistente);
            redirectAttributes.addFlashAttribute("mensaje",
                    "El programa se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Ocurrió un error al actualizar el programa.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/empresaPrograma";
    }

    // Eliminar un programa
    @GetMapping("/eliminar/{id}")
    public String eliminarPrograma(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            programaService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "La carrera se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar la carrera.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/empresaPrograma";
    }

    // Método para cambiar el estado del carrera a inactivo

    @PostMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            EmpresaPrograma empresaPrograma = programaService.obtenerPorId(id);

            if (empresaPrograma != null) {
                if ("A".equals(empresaPrograma.getEstado())) {
                    empresaPrograma.setEstado("I");
                } else {
                    empresaPrograma.setEstado("A");
                }
                empresaPrograma.setFechaMod(LocalDateTime.now());
                programaService.guardar(empresaPrograma);
            }
            redirectAttributes.addFlashAttribute("mensaje",
                    "El estado del programa se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Ocurrió un error al actualizar el estado del programa .");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }

        return "redirect:/empresaPrograma";
    }

}
