package com.industrial.pasantias.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.industrial.pasantias.Servicio.CarreraService;


@Controller
public class ReportesContoller {
        
    @Autowired
    private CarreraService carreraService;


    @GetMapping("/reporteria")
    public String getMethodName(Model model) {
        model.addAttribute("reporte", ""); 
        model.addAttribute("reporte2", ""); 
        model.addAttribute("carreras",carreraService.obtenerTodos()); 
        return "reportes/reporteria";
    }

    
    @GetMapping("/reporteria/{reporte}")
    public String reportesFiltrados(@PathVariable String reporte , Model model) {
        model.addAttribute("reporte", reporte); 
        model.addAttribute("reporte2", reporte); 
        model.addAttribute("carreras",carreraService.obtenerTodos()); 
        return "reportes/reporteria";
    }
    
}
