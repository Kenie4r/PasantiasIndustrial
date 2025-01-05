package com.industrial.pasantias.Controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.industrial.pasantias.Model.Carrera;
import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Model.EmpresaPrograma;
import com.industrial.pasantias.Model.EstudianteEntity;
import com.industrial.pasantias.Model.Pasantia;
import com.industrial.pasantias.Servicio.CarreraService;
import com.industrial.pasantias.Servicio.EmpresaService;
import com.industrial.pasantias.Servicio.EstudianteService;
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
    private EmpresaService empresaService;

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

        // Programas de empresa
        //List<EmpresaPrograma> programasEmpresas = programaService.ObternerTodo();
        //List<SelectListItem> programas = new ArrayList<>();
        //for (EmpresaPrograma p : programasEmpresas) {
        //    String nombreEmpresa = p.getEmpresa().getNombre();
        //    SelectListItem programa = new SelectListItem(String.valueOf(p.getIdPrograma()), "[" + nombreEmpresa + "] " + p.getNombrePrograma());
        //    programas.add(programa);
        //}

        // Estados
        List<SelectListItem> estados = pasantiaService.obtenerEstadosPasantia();

        // Pasantia vacia
        Pasantia pasantia = new Pasantia();

        model.addAttribute("carreras", carreras);
        //model.addAttribute("programas", programas);
        model.addAttribute("estados", estados);
        model.addAttribute("pasantia", pasantia);
        
        return "pasantias/guardar";
    }

    @PostMapping
    public String guardarPasantia(@ModelAttribute Pasantia pasantia, Model model) {
        try {
            //pasantia.setFechaInicio(LocalDateTime.now());;
            pasantia.setFechaCrea(LocalDateTime.now());
            pasantia.setUsuCrea("test");
            String anioFull = '2' + pasantia.getAnioEstudiante();
            LocalDateTime fecha = LocalDateTime.of(Integer.parseInt(anioFull), Month.JANUARY, 1, 0, 0, 0);
            pasantia.setFechaIngresoUniversidad(fecha);
            pasantiaService.guardar(pasantia);
            return "redirect:/pasantias";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(pasantia);
            return "pasantias/guardar";
        }
    }

    // Endpoint
    @GetMapping("/obtenerCarrera/{id}")
    public ResponseEntity<?> obtenerCarrera(@PathVariable Integer id) {
        Carrera carrera = carreraService.obtenerPorId(id);
        return ResponseEntity.ok(carrera);
    }

    @GetMapping("/obtenerEstudiantesPorCarrera/{id}")
    public ResponseEntity<?> obtenerEstudiantesPorCarrera(@PathVariable Integer id){
        List<EstudianteEntity> estudiantes = estudianteService.obtenerPorIdCarrera(id);
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/obtenerCorrelativoPorCarrera/{id}")
    public ResponseEntity<?> obtenerCorrelativoPorCarrera(@PathVariable Integer id){
        Integer correlativo = pasantiaService.ObtenerCorrelativoParaNuevaPasantia(id);
        return ResponseEntity.ok(correlativo);
    }
}
