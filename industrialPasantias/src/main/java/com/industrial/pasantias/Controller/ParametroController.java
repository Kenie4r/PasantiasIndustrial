package com.industrial.pasantias.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.industrial.pasantias.Model.Parametro;
import com.industrial.pasantias.Servicio.ParametroService;

@Controller
public class ParametroController {
    @Autowired
    private ParametroService parametroService;

    // Listar parametros
    @GetMapping("/parametros")
    public String listarParametros(Model model) {
        List<Parametro> parametros = parametroService.obtenerTodos();
        model.addAttribute("parametros", parametros);
        return "parametros/listado_parametros";
    }

    // Mostrar formulario para crear un nuevo parametro
    @GetMapping("/parametros/nuevo")
    public String mostrarFormularioNuevaParametro(Model model) {
        model.addAttribute("parametro", new Parametro());
        return "parametros/crear_editar_parametro";
    }

    // Guardar parametro
    @PostMapping("/parametros")
    public String guardarParametro(@ModelAttribute Parametro parametro, RedirectAttributes redirectAttributes,
            Model model) {
        try {
            parametroService.guardar(parametro);
            redirectAttributes.addFlashAttribute("mensaje", "El parámetro se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("mensaje", "Ocurrió un error al guardar el parámetro.");
            return "parametros/crear_editar_parametro";
        }
        return "redirect:/parametros";
    }

    // Mostrar formulario para editar un parametro
    @GetMapping("/parametros/editar/{id}")
    public String mostrarFormularioModificarParametro(@PathVariable Integer id, Model model) {
        Parametro parametro = parametroService.obtenerPorId(id);
        if (parametro == null) {
            return "redirect:/parametros";
        }
        model.addAttribute("parametro", parametro);
        return "parametros/crear_editar_parametro";
    }

    // Actualizar parametro
    @PostMapping("/parametros/editar/{id}")
    public String actualizarParametro(@PathVariable Integer id, @ModelAttribute Parametro parametro,
            RedirectAttributes redirectAttributes, Model model) {
        try {
            Parametro parametroExistente = parametroService.obtenerPorId(id);
            if (parametro == null) {
                return "redirect:/parametros";
            }

            parametroExistente.setDescripcion(parametro.getDescripcion());
            parametroExistente.setValor(parametro.getValor());
            parametroExistente.setCategoria(parametro.getCategoria());
            //parametroExistente.setFechaMod(LocalDateTime.now());

            parametroService.guardar(parametroExistente);
            redirectAttributes.addFlashAttribute("mensaje", "El parámetro se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/parametros";
        } catch (Exception e) {
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("mensaje", "Ocurrió un error al actualizar el parámetro.");
            return "parametros/crear_editar_parametro";
        }
    }

    // Eliminar un parametro
    @GetMapping("/parametros/eliminar/{id}")
    public String eliminarParametro(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            parametroService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "El parámetro se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el parámetro.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/parametros";
    }
}
