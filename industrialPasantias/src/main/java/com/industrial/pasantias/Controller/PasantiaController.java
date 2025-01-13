package com.industrial.pasantias.Controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.industrial.pasantias.Model.Carrera;
import com.industrial.pasantias.Model.EmpresaPrograma;
import com.industrial.pasantias.Model.EstudianteEntity;
import com.industrial.pasantias.Model.Pasantia;
import com.industrial.pasantias.Model.PasantiaPrograma;
import com.industrial.pasantias.Model.PK.PasantiaProgramaPK;
import com.industrial.pasantias.Servicio.CarreraService;
import com.industrial.pasantias.Servicio.EstudianteService;
import com.industrial.pasantias.Servicio.PasantiaProgramaService;
import com.industrial.pasantias.Servicio.PasantiaService;
import com.industrial.pasantias.Servicio.ProgramaService;
import com.industrial.pasantias.ViewModel.SelectListItem;

@Controller
@RequestMapping("/pasantias")
public class PasantiaController {
    @Autowired
    private PasantiaService pasantiaService;

    @Autowired
    private CarreraService carreraService;

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private ProgramaService programaService;

    @Autowired
    private PasantiaProgramaService pasantiaProgramaService;

    // Index -----------------------------------------------------------
    @GetMapping
    public String listarPasantias(Model model) {
        List<Pasantia> pasantias = pasantiaService.obtenerTodos();
        model.addAttribute("pasantias", pasantias);
        return "pasantias/pasantias";
    }

    // Crear -----------------------------------------------------------
    @GetMapping("/nuevo")
    public String nuevaPasantia(Model model) {
        // Carreras
        List<Carrera> carreras = carreraService.obtenerTodos();

        // Estados
        List<SelectListItem> estados = pasantiaService.obtenerEstadosPasantia();

        // Pasantia vacia
        Pasantia pasantia = new Pasantia();

        model.addAttribute("carreras", carreras);
        // model.addAttribute("programas", programas);
        model.addAttribute("estados", estados);
        model.addAttribute("pasantia", pasantia);

        return "pasantias/guardar";
    }

    @PostMapping
    public String guardarPasantia(@ModelAttribute Pasantia pasantia, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            // Obtener usuario logueado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "Anónimo";

            // pasantia.setFechaInicio(LocalDateTime.now());;
            pasantia.setFechaCrea(LocalDateTime.now());
            pasantia.setUsuCrea(username);
            String anioFull = '2' + pasantia.getAnioEstudiante();
            LocalDateTime fecha = LocalDateTime.of(Integer.parseInt(anioFull), Month.JANUARY, 1, 0, 0, 0);
            pasantia.setFechaIngresoUniversidad(fecha);
            pasantiaService.guardar(pasantia);
            redirectAttributes.addFlashAttribute("mensaje", "La práctica profesional se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/pasantias";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("mensaje", "Ocurrió un error al guardar la práctica profesional.");
            model.addAttribute("tipoMensaje", "error");
            return "pasantias/guardar";
        }
    }

    // Proyectos -----------------------------------------------------------
    @GetMapping("/proyectos/{id}")
    public String listarProyectos(@PathVariable Integer id, Model model) {
        Pasantia pasantia = pasantiaService.obtenerPorIdPasantia(id);
        List<PasantiaPrograma> pasantiaProgramas = pasantiaProgramaService.obtenerPorIdPasantia(id);
        Integer horasTotales = pasantiaService.ObtenerHorasTotalesPasantia(id);
        model.addAttribute("pasantia", pasantia);
        model.addAttribute("programas", pasantiaProgramas);
        model.addAttribute("varHorasTotales", horasTotales);
        return "pasantias/proyectos";
    }

    // Formulario Crear proyecto
    // ------------------------------------------------------
    @GetMapping("/proyectos/nuevo/{idPasantia}")
    public String nuevoProyecto(@PathVariable Integer idPasantia, Model model) {
        //
        Pasantia pasantia = pasantiaService.obtenerPorIdPasantia(idPasantia);
        List<EmpresaPrograma> empresasProgramas = programaService.ObternerTodo();
        //
        PasantiaPrograma pasantiaPrograma = new PasantiaPrograma();
        PasantiaProgramaPK id = new PasantiaProgramaPK();
        id.setPasantia(pasantia);
        pasantiaPrograma.setId(id);

        // Estados
        List<SelectListItem> estados = pasantiaService.obtenerEstadosPasantia();

        model.addAttribute("proyecto", pasantiaPrograma);
        model.addAttribute("pasantia", pasantia);
        model.addAttribute("empresasProgramas", empresasProgramas);
        model.addAttribute("estados", estados);
        model.addAttribute("accion", "guardar");
        return "pasantias/proyectoGuardar";

    }

    // Guardar proyecto
    @PostMapping("/proyectos")
    public String guardarProyecto(@ModelAttribute PasantiaPrograma pasantiaPrograma, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            // Obtener usuario logueado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "Anónimo";

            pasantiaPrograma.setFechaCrea(LocalDateTime.now());
            pasantiaPrograma.setUsuCrea(username);

            pasantiaProgramaService.guardar(pasantiaPrograma);
            redirectAttributes.addFlashAttribute("mensaje", "La práctica profesional se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/pasantias/proyectos/" + pasantiaPrograma.getId().getPasantia().getIdPasantia();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

            //
            Pasantia pasantia = pasantiaService
                    .obtenerPorIdPasantia(pasantiaPrograma.getId().getPasantia().getIdPasantia());
            List<EmpresaPrograma> empresasProgramas = programaService.ObternerTodo();

            // Estados
            List<SelectListItem> estados = pasantiaService.obtenerEstadosPasantia();

            model.addAttribute("proyecto", pasantiaPrograma);
            model.addAttribute("pasantia", pasantia);
            model.addAttribute("empresasProgramas", empresasProgramas);
            model.addAttribute("estados", estados);

            model.addAttribute("mensaje", "Ocurrió un error al guardar la práctica profesional.");
            model.addAttribute("tipoMensaje", "error");
            return "pasantias/proyectoGuardar";
        }
    }

    // Formulario Editar proyecto
    // ------------------------------------------------------
    @GetMapping("/proyectos/editar/pasantia/{idPasantia}/programa/{idPrograma}")
    public String editarProyecto(@PathVariable Integer idPasantia, @PathVariable Integer idPrograma, Model model) {
        //
        Pasantia pasantia = pasantiaService.obtenerPorIdPasantia(idPasantia);
        List<EmpresaPrograma> empresasProgramas = programaService.ObternerTodo();
        //
        Optional<PasantiaPrograma> pasantiaPrograma = pasantiaProgramaService.obtenerPorIdPasantiaPrograma(idPasantia,
                idPrograma);

        // Estados
        List<SelectListItem> estados = pasantiaService.obtenerEstadosPasantia();

        model.addAttribute("proyecto", pasantiaPrograma);
        model.addAttribute("pasantia", pasantia);
        model.addAttribute("empresasProgramas", empresasProgramas);
        model.addAttribute("estados", estados);
        model.addAttribute("accion", "editar");
        return "pasantias/proyectoGuardar";
    }

    // Endpoint
    @GetMapping("/obtenerCarrera/{id}")
    public ResponseEntity<?> obtenerCarrera(@PathVariable Integer id) {
        Carrera carrera = carreraService.obtenerPorId(id);
        return ResponseEntity.ok(carrera);
    }

    @GetMapping("/obtenerEstudiantesPorCarrera/{id}")
    public ResponseEntity<?> obtenerEstudiantesPorCarrera(@PathVariable Integer id) {
        List<EstudianteEntity> estudiantes = estudianteService.obtenerPorIdCarrera(id);
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/obtenerCorrelativoPorCarrera/{id}")
    public ResponseEntity<?> obtenerCorrelativoPorCarrera(@PathVariable Integer id) {
        Integer correlativo = pasantiaService.ObtenerCorrelativoParaNuevaPasantia(id);
        return ResponseEntity.ok(correlativo);
    }

    @PostMapping("/cambiarEstadoPractica")
    public ResponseEntity<?> cambiarEstadoPractica(@RequestBody Pasantia pasantia) {
        pasantia = pasantiaService.obtenerPorIdPasantia(pasantia.getIdPasantia());

        String estadoNuevo = pasantia.getEstado().equals("E") ? "F" : "E";

        pasantia.setEstado(estadoNuevo);
        pasantiaService.guardar(pasantia);
        return ResponseEntity.ok(pasantia);
    }
}
