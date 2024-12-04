package com.industrial.pasantias.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Model.EmpresaPrograma;
import com.industrial.pasantias.Model.Materia;
import com.industrial.pasantias.Repository.ProgramaRepository;
import com.industrial.pasantias.Servicio.EmpresaService;
import com.industrial.pasantias.Servicio.MateriaService;
import com.industrial.pasantias.Servicio.ProgramaService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/empresaPrograma")
public class ProgramaController {
    private final ProgramaService progama;
    private final EmpresaService empresaService;

    public ProgramaController(ProgramaService progama, EmpresaService empresaService) {
        this.progama = progama;
        this.empresaService = empresaService;
    }

    @GetMapping(path ={"", "/"})

    public String listarProgramas(Model model) {
        List<EmpresaPrograma> programas = progama.ObternerTodo(); // Obtiene todos los programas
        model.addAttribute("programas", programas);
        return "empresaPrograma/index"; // Vista que muestra los programas
    }

    @GetMapping("/{id}")
    public String listarPrograma(@PathVariable Integer id, Model model) {
        List<EmpresaPrograma> programas = progama.obtenerPorIdEmpresa(id);
        model.addAttribute("programas", programas); // Pas
        return "empresaPrograma/index";
    }

    @GetMapping("/nuevo")
    public String FormGuardar(Model model) {
        List<Empresa> empresas = empresaService.listarEmpresas();
        model.addAttribute("empresas", empresas);
        model.addAttribute("programa", new EmpresaPrograma());
        return "empresaPrograma/crear";
    }

    // Maneja la solicitud POST para guardar el nuevo programa
    @PostMapping("/guardar")
    public String guardarPrograma(@ModelAttribute("programa") EmpresaPrograma programa, Model model) {
        // Guarda el nuevo programa en la base de datos
        progama.guardar(programa);
        // Redirige al listado de programas
        return "/empresaPrograma/";
    }
}
