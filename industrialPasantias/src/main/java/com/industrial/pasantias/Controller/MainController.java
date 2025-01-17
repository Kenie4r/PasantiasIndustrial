package com.industrial.pasantias.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import com.industrial.pasantias.Servicio.PasantiaService;

@Controller

public class MainController {
    @Autowired
    private PasantiaService pasantiaService;

    @RequestMapping(method = RequestMethod.GET, path = { "/", "", "/inicio", "/index" })
    public String obtenerPaginaPrincipal(Model model) {
        Integer practivasActivas = pasantiaService.obtenerPracticasProfesionalesActivas();
        Integer practivasActivasIngenieria = pasantiaService.obtenerPracticasProfesionalesActivasIngenieria();
        Integer practivasActivasTecnico = pasantiaService.obtenerPracticasProfesionalesActivasTecnico();

        model.addAttribute("practivasActivas", practivasActivas);
        model.addAttribute("practivasActivasIngenieria", practivasActivasIngenieria);
        model.addAttribute("practivasActivasTecnico", practivasActivasTecnico);
        return "index.html";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String requestMethodName() {
        return "login.html";
    }

    /*
     * @RequestMapping(path = "/access", method=RequestMethod.POST)
     * public String requestMethodName(@RequestParam String param) {
     * 
     * return new String();
     * }
     */

}
