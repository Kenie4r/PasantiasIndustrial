package com.industrial.pasantias.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.industrial.pasantias.Model.Carrera;
import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Model.Programa;
import com.industrial.pasantias.Model.Materia;
import com.industrial.pasantias.Servicio.CarreraService;
import com.industrial.pasantias.Servicio.EmpresaService;
import com.industrial.pasantias.Servicio.MateriaService;
import com.industrial.pasantias.Servicio.ProgramaService;

@Controller
@RequestMapping("/programas")
public class ProgramaController {
    private final ProgramaService programaService;
    private final EmpresaService empresaService;
    private final MateriaService materiaService;
    private final CarreraService carreraService;

    public ProgramaController(ProgramaService progama, EmpresaService empresaService, MateriaService materiaService,
            CarreraService carreraService) {
        this.programaService = progama;
        this.empresaService = empresaService;
        this.materiaService = materiaService;
        this.carreraService = carreraService;
    }

    @GetMapping(path = { "", "/" })
    public String listarProgramas(@RequestParam(value = "idEmpresa", required = false) Integer idEmpresa, Model model) {
        List<Empresa> empresas = empresaService.listarEmpresas(); // Listado de empresas para el select
        List<Programa> programas;

        if (idEmpresa != null) {
            // Filtrar programas por la empresa seleccionada
            programas = programaService.obtenerPorIdEmpresa(idEmpresa);
            model.addAttribute("idEmpresa", idEmpresa); // Preseleccionar empresa en el select
        } else {
            // Mostrar todos los programas si no hay filtro
            programas = programaService.ObternerTodo();
        }

        model.addAttribute("empresas", empresas);
        model.addAttribute("programasEmpresa", programas); // Cambia "programas" a "programasEmpresa"
        return "programas/index";
    }

    // Mostrar programa por id
    @GetMapping("/{id}")
    public String listarPrograma(@PathVariable Integer id, Model model) {
        List<Programa> programas = programaService.obtenerPorIdEmpresa(id);
        Empresa empresa = null;

        if (id != null) {
            empresa = empresaService.obtenerPorId(id);
            model.addAttribute("empresa", empresa.getNombre());
        }

        model.addAttribute("programasEmpresa", programas);
        model.addAttribute("empresaId", id);
        return "programas/index";
    }

    // Mostrar formulario nuevo programa
    @GetMapping("/nuevo")
    public String FormGuardar(@RequestParam(value = "idEmpresa", required = false) Integer idEmpresa, Model model) {
        Empresa empresa = null;

        // Si se proporciona un ID de empresa, intenta obtener la empresa
        if (idEmpresa != null) {
            empresa = empresaService.obtenerPorId(idEmpresa);
            model.addAttribute("empresaId", empresa.getIdEmpresa());
            model.addAttribute("empresa", empresa.getNombre());
        }

        List<Empresa> empresas = empresaService.listarEmpresas();
        List<Materia> materias = materiaService.listar();
        List<Carrera> carreras = carreraService.obtenerCarrerasActivas();

        model.addAttribute("carrera", carreras);
        model.addAttribute("empresas", empresas);
        model.addAttribute("materia", materias);
        model.addAttribute("programa", new Programa());
        return "programas/crear_editar_programa";
    }

    // Maneja la solicitud POST para guardar el nuevo programa
    @PostMapping("/guardar")
    public String guardarPrograma(@ModelAttribute("programa") Programa programa, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            /*
             * VALIDACION SI ES ASIGNATURA ID_EMPRESA = NULL
             * if (programa.getTipoPrograma().equals("Asignatura"))
             * programa.setEmpresa(null);
             */
            if (programa.getTipoPrograma().equals("Empresa"))
                programa.setMateria(null);

            // Validar las fechas de inicio y final programa
            LocalDate fechaIni = programa.getFechaIni();
            LocalDate fechaFi = programa.getFechaFi();

            if (fechaFi.isAfter(fechaIni)) {
                // Guarda el nuevo programa en la base de datos
                programaService.guardar(programa);
                redirectAttributes.addFlashAttribute("mensaje", "El programa se guardó correctamente.");
                redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            } else {
                redirectAttributes.addFlashAttribute("tipoMensaje", "error");
                redirectAttributes.addFlashAttribute("mensaje", "La fecha final debe ser mayor a la inicial.");
            }
            return "redirect:/programas/empresa/" + programa.getEmpresa().getIdEmpresa();

        } catch (Exception e) {
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("mensaje", "Ocurrió un error al guardar el programa.");
            return "programas/crear_editar_programa";
        }

    }

    // Mostrar formulario para editar el programa
    @SuppressWarnings("null")
    @GetMapping("/editar/{id}")
    public String mostrarFormularioModificarPrograma(@PathVariable Integer id,
            Model model) {
        Programa programas = programaService.obtenerPorId(id);
        if (programas == null) {
            return "redirect:/programas/empresa/" + programas.getEmpresa().getIdEmpresa();
        }

        model.addAttribute("empresaId", programas.getEmpresa().getIdEmpresa());
        model.addAttribute("empresa", programas.getEmpresa().getNombre());

        List<Empresa> empresas = empresaService.listarEmpresas();
        List<Materia> materias = materiaService.listar();
        List<Carrera> carreras = carreraService.obtenerCarrerasActivas();

        if (programas.getTipoPrograma().equals("Asignatura")) {
            Integer idCarreraMateria = materiaService.obtenerCarreraPorIdMateria(programas.getMateria().getIdMateria());
            model.addAttribute("programaIdCarrera", idCarreraMateria);
            model.addAttribute("programaIdMateria", programas.getMateria().getIdMateria());

            System.out.println("idCarreraMateria" + idCarreraMateria);
            System.out.println("programas.getMateria().getIdMateria()" + programas.getMateria().getIdMateria());
        }

        model.addAttribute("empresas", empresas);
        model.addAttribute("materia", materias);
        model.addAttribute("programa", programas);
        model.addAttribute("carrera", carreras);

        return "programas/crear_editar_programa";
    }

    // Mostrar programa por empresa
    @GetMapping("/empresa/{id}")
    public String listarProgramasEmpresa(@PathVariable Integer id, Model model) {
        List<Programa> programas = programaService.obtenerPorIdEmpresa(id);
        Empresa empresa = null;

        if (id != null) {
            empresa = empresaService.obtenerPorId(id);
            model.addAttribute("empresa", empresa.getNombre());
        }

        model.addAttribute("programasEmpresa", programas);
        model.addAttribute("empresaId", id);
        return "programas/index";
    }

    // Actualizar programas

    @SuppressWarnings("null")
    @PostMapping("/editar/{id}")
    public String actualizarPrograma(@PathVariable Integer id, @ModelAttribute Programa programas,
            RedirectAttributes redirectAttributes) {
        try {
            Programa programaExistente = programaService.obtenerPorId(id);
            if (programas == null) {
                return "redirect:/programas/empresa/" + programas.getEmpresa().getIdEmpresa();
            }

            programaExistente.setNombrePrograma(programas.getNombrePrograma());
            programaExistente.setTipoPrograma(programas.getTipoPrograma());
            programaExistente.setFechaIni(programas.getFechaIni());
            programaExistente.setFechaFi(programas.getFechaFi());
            programaExistente.setEmpresa(programas.getEmpresa());
            programaExistente.setMateria(programas.getMateria());
            programaExistente.setEstado(programas.getEstado());
            programaExistente.setNombreResponsable(programas.getNombreResponsable());
            programaExistente.setAreaRealizacion(programas.getAreaRealizacion());
            programaExistente.setOtrosDetalles(programas.getOtrosDetalles());
            programaExistente.setObservaciones(programas.getObservaciones());
            programaExistente.setCorreoResponsable(programas.getCorreoResponsable());
            programaExistente.setTelResponsable(programas.getTelResponsable());

            if (programas.getTipoPrograma().equals("Empresa"))
                programaExistente.setMateria(null);

            programaService.guardar(programaExistente);
            redirectAttributes.addFlashAttribute("mensaje",
                    "El programa se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Ocurrió un error al actualizar el programa.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/programas/empresa/" + programas.getEmpresa().getIdEmpresa();
    }

    // Eliminar un programa
    @GetMapping("/eliminar/{id}")
    public String eliminarPrograma(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Programa programas = programaService.obtenerPorId(id);
        try {
            programaService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "El programa se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el programa.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/programas/empresa/" + programas.getEmpresa().getIdEmpresa();
    }

    // Método para cambiar el estado del programa a inactivo
    @SuppressWarnings("null")
    @PostMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Programa programas = programaService.obtenerPorId(id);
        try {
            if (programas != null) {
                if ("A".equals(programas.getEstado())) {
                    programas.setEstado("I");
                } else {
                    programas.setEstado("A");
                }
                programas.setFechaMod(LocalDateTime.now());
                programaService.guardar(programas);
            }
            redirectAttributes.addFlashAttribute("mensaje",
                    "El estado del programa se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Ocurrió un error al actualizar el estado del programa .");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }

        return "redirect:/programas/empresa/" + programas.getEmpresa().getIdEmpresa();
    }

    // Método para obtener las asignaturas de una carrera
    @GetMapping("/obtenerAsignaturaPorCarrera/{id}")
    public ResponseEntity<List<Materia>> obtenerAsignaturasPorCarrera(@PathVariable Integer id) {
        List<Materia> materias = materiaService.obtenerPorCarreraId(id);
        if (materias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(materias);
    }
}
