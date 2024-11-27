package com.industrial.pasantias.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import com.industrial.pasantias.Model.Usuario;
import com.industrial.pasantias.Servicio.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Listar usuarios
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    // Mostrar formulario para crear un nuevo usuario
    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "crear_editar_usuario";
    }

    // Guardar usuario (Crear o Editar)
    @PostMapping("/usuarios")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        // Hashear la contraseña solo al crear el usuario
        if (usuario.getIdUsuario() == null) {
            String hashedPassword = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(hashedPassword);
            usuario.setFechaCrea(LocalDateTime.now());
            usuario.setEstado("A");
        } else {

            usuario.setFechaMod(LocalDateTime.now());
        }
        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    // Mostrar formulario para editar un usuario
    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioModificarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        System.out.println("USUARIO: " + usuario);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "crear_editar_usuario";
    }

    // Actualizar usuario
    @PostMapping("/usuarios/editar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute Usuario usuario) {
        Usuario usuarioExistente = usuarioService.obtenerPorId(id);
        if (usuario == null) {
            return "redirect:/usuarios";
        }

        usuarioExistente.setNombres(usuario.getNombres());
        usuarioExistente.setApellidos(usuario.getApellidos());
        usuarioExistente.setCorreo(usuario.getCorreo());
        usuarioExistente.setUsername(usuario.getUsername());
        // usuarioExistente.setIdRol();
        usuarioExistente.setFechaMod(LocalDateTime.now());

        // Hashea la contraseña solo si el campo no está vacío
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        usuarioService.guardar(usuarioExistente);
        return "redirect:/usuarios";
    }

    // Eliminar un usuario
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }

    // Método para cambiar el estado del usuario a inactivo
    @PostMapping("/usuarios/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Long id) {        
        Usuario usuario = usuarioService.obtenerPorId(id);

        if (usuario != null) {
            if ("A".equals(usuario.getEstado())) {
                usuario.setEstado("I"); 
            } else {
                usuario.setEstado("A"); 
            }
            usuarioService.guardar(usuario);
        }

        return "redirect:/usuarios";
    }
}