package com.industrial.pasantias.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "usuarios/listado_usuarios";
    }

    // Mostrar formulario para crear un nuevo usuario
    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/crear_editar_usuario";
    }

    // Guardar usuario (Crear o Editar)
    @PostMapping("/usuarios")
    public String guardarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
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
            redirectAttributes.addFlashAttribute("mensaje", "El usuario se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el usuario.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/usuarios";
    }

    // Mostrar formulario para editar un usuario
    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioModificarUsuario(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        System.out.println("USUARIO: " + usuario);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/crear_editar_usuario";
    }

    // Actualizar usuario
    @PostMapping("/usuarios/editar/{id}")
    public String actualizarUsuario(@PathVariable Integer id, @ModelAttribute Usuario usuario,
            RedirectAttributes redirectAttributes) {
        try {
            Usuario usuarioExistente = usuarioService.obtenerPorId(id);
            if (usuario == null) {
                return "redirect:/usuarios";
            }

            usuarioExistente.setNombres(usuario.getNombres());
            usuarioExistente.setApellidos(usuario.getApellidos());
            usuarioExistente.setCorreo(usuario.getCorreo());
            usuarioExistente.setUsername(usuario.getUsername());
            usuarioExistente.setIdRol(usuario.getIdRol());
            usuarioExistente.setFechaMod(LocalDateTime.now());

            // Hashea la contraseña solo si el campo no está vacío
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            usuarioService.guardar(usuarioExistente);
            redirectAttributes.addFlashAttribute("mensaje", "El usuario se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al actualizar el usuario.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/usuarios";
    }

    // Eliminar un usuario
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "El usuario se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el usuario.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/usuarios";
    }

    // Método para cambiar el estado del usuario a inactivo
    @PostMapping("/usuarios/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.obtenerPorId(id);

            if (usuario != null) {
                if ("A".equals(usuario.getEstado())) {
                    usuario.setEstado("I");
                } else {
                    usuario.setEstado("A");
                }
                usuarioService.guardar(usuario);
            }
            redirectAttributes.addFlashAttribute("mensaje", "El usuario se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al actualizar el usuario.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/usuarios";
    }
}