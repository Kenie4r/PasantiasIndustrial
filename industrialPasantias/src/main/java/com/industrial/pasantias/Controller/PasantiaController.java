package com.industrial.pasantias.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.industrial.pasantias.Model.Pasantia;
import com.industrial.pasantias.Servicio.PasantiaService;

@Controller
@RequestMapping("/pasantias")
public class PasantiaController {
    @Autowired
    private PasantiaService pasantiaService;

    // Index -----------------------------------------------------------
    @GetMapping
    public String listarPasantias(Model model) {
        List<Pasantia> pasantias = pasantiaService.obtenerTodos();
        model.addAttribute("pasantias", pasantias);
        return "pasantias/pasantias";
    }
}
