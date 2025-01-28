package com.industrial.pasantias.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.industrial.pasantias.Model.Pasantia;
import com.industrial.pasantias.Model.Usuario;
import com.industrial.pasantias.Servicio.PasantiaService;
import com.industrial.pasantias.Servicio.UsuarioService;

@Controller

public class MainController {
    @Autowired
    private PasantiaService pasantiaService;

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(method = RequestMethod.GET, path = { "/", "", "/inicio", "/index" })
    public String obtenerPaginaPrincipal(Model model) {
        Integer practivasActivas = pasantiaService.obtenerPracticasProfesionalesActivas();
        Integer practivasActivasIngenieria = pasantiaService.obtenerPracticasProfesionalesActivasIngenieria();
        Integer practivasActivasTecnico = pasantiaService.obtenerPracticasProfesionalesActivasTecnico();

        // Actividad reciente
        List<Pasantia> ultimasPracticas = pasantiaService.obtenerUltimasPracticasProfesionales();
        model.addAttribute("ultimasPracticas", ultimasPracticas);

        model.addAttribute("practivasActivas", practivasActivas);
        model.addAttribute("practivasActivasIngenieria", practivasActivasIngenieria);
        model.addAttribute("practivasActivasTecnico", practivasActivasTecnico);
        return "index.html";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String requestMethodName() {
        return "login.html";
    }

    @ModelAttribute("datosUsuario")
    public Usuario addDatosUsuarioToModel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "Anónimo";

        if (!"Anónimo".equals(username) && username != null) {
            return usuarioService.obtenerPorUsername(username);
        }
        return null;
    }

    /*
     * @RequestMapping(path = "/access", method=RequestMethod.POST)
     * public String requestMethodName(@RequestParam String param) {
     * 
     * return new String();
     * }
     */

}
