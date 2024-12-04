package com.industrial.pasantias.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.industrial.pasantias.Model.Estudiante;
import com.industrial.pasantias.Model.Pasantia;
import com.industrial.pasantias.Servicio.CarreraService;
import com.industrial.pasantias.Servicio.EmpresaService;
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
        List<Carrera> carreras = carreraService.obtenerTodos();
        // Programas de empresa
        List<EmpresaPrograma> programasEmpresas = programaService.ObternerTodo();
        List<SelectListItem> programas = new ArrayList<>();
        for (EmpresaPrograma p : programasEmpresas) {
            String nombreEmpresa = p.getEmpresa().getNombre();
            SelectListItem programa = new SelectListItem(String.valueOf(p.getIdPrograma()), "[" + nombreEmpresa + "] " + p.getNombrePrograma());
            programas.add(programa);
        }

        // Estados
        List<SelectListItem> estados = new ArrayList<>();
        SelectListItem estado1 = new SelectListItem("E", "En Proceso");
        estados.add(estado1);
        estado1 = new SelectListItem("F", "Finalizado");
        estados.add(estado1);
        Pasantia pasantia = new Pasantia();

        List<Pasantia> pasantias = pasantiaService.obtenerTodos();
        int totalPasantias = pasantias.size();
        pasantia.setCorrelativo(totalPasantias + 1);
        pasantia.setFechaInicio(LocalDateTime.now());

        model.addAttribute("carreras", carreras);
        model.addAttribute("programas", programas);
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
        List<Estudiante> estudiantes = new ArrayList<>();
        Estudiante estudiante1 = new Estudiante();
        Estudiante estudiante2 = new Estudiante();
        if(id % 2 == 0){
            estudiante1.setCarnet("MH212532");
            estudiante1.setNombres("Diego Fernando");
            estudiante1.setApellidos("Mancía Hernández");

            estudiante2.setCarnet("LM212558");
            estudiante2.setNombres("Fernando José");
            estudiante2.setApellidos("Lemus Mejía");
        }else{
            estudiante1.setCarnet("GM151456");
            estudiante1.setNombres("Juan Ernesto");
            estudiante1.setApellidos("Guerrero Montes");

            estudiante2.setCarnet("NC212543");
            estudiante2.setNombres("Rafael Alexander");
            estudiante2.setApellidos("Najarro Campos");
        }

        estudiantes.add(estudiante1);
        estudiantes.add(estudiante2);

        return ResponseEntity.ok(estudiantes);
    }
}
