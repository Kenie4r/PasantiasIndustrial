package com.industrial.pasantias.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.industrial.pasantias.Model.Usuario;
import com.industrial.pasantias.Servicio.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

@Controller

public class MainController {
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(method = RequestMethod.GET, path = { "/", "", "/inicio", "/index" })
    public String obtenerPaginaPrincipal(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "Anónimo";
        
        Usuario datosUsuario = new Usuario();
        if (username != "Anónimo" && username != null) {
            datosUsuario = usuarioService.obtenerPorUsername(username);        
        }
        model.addAttribute("datosUsuario", datosUsuario);
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
