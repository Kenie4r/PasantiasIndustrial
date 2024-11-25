package com.industrial.pasantias.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prueba")
public class PruebaController {


    @GetMapping("/home")
    public String Prueba(Model model){

        model.addAttribute("mensaje", "¡Hola desde el controlador!");
        return "prueba";
    }

}
