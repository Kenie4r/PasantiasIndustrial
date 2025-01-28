package com.industrial.pasantias.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.industrial.pasantias.Model.Usuario;
import com.industrial.pasantias.Servicio.UsuarioService;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute("datosUsuario")
    public Usuario addDatosUsuarioToModel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "Anónimo";

        if (!"Anónimo".equals(username) && username != null) {
            return usuarioService.obtenerPorUsername(username);
        }
        return null;
    }
}
