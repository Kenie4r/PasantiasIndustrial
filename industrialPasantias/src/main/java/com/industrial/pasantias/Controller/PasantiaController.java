package com.industrial.pasantias.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.industrial.pasantias.Model.Carrera;
import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Model.EmpresaPrograma;
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
            //Empresa empresa = empresaService.get
            SelectListItem programa = new SelectListItem(String.valueOf(p.getIdPrograma()), "");
            programas.add(programa);
        }

        // Estados
        List<SelectListItem> estados = new ArrayList<>();
        SelectListItem estado1 = new SelectListItem("E", "En Proceso");
        estados.add(estado1);
        estado1 = new SelectListItem("F", "Finalizado");
        estados.add(estado1);
        Pasantia pasantia = new Pasantia();

        pasantia.setCorrelativo(1);

        model.addAttribute("carreras", carreras);
        model.addAttribute("programas", programas);
        model.addAttribute("estados", estados);
        model.addAttribute("pasantia", pasantia);
        
        return "pasantias/guardar";
    }
}
