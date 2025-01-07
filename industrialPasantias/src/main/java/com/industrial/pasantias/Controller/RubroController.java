package com.industrial.pasantias.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.industrial.pasantias.Model.Rubro;
import com.industrial.pasantias.Servicio.RubroService;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/rubros")
public class RubroController {
    @Autowired
    private RubroService rubroService;

    // Index -----------------------------------------------------------
    @GetMapping
    public String listarRubros(Model model) {
        List<Rubro> rubros = rubroService.obtenerTodos();
        model.addAttribute("rubros", rubros);
        return "rubros/rubros";
    }

    // Nuevo rubro -----------------------------------------------------
    @GetMapping("/nuevo")
    public String nuevoRubro(Model model) {
        model.addAttribute("rubro", new Rubro());
        return "rubros/guardar";
    }

    @PostMapping
    public String guardarRubro(@ModelAttribute Rubro rubro, RedirectAttributes redirectAttributes, Model model) {
        try {
            rubro.setEstado("A");
            rubro.setFechaCrea(LocalDateTime.now());
            rubroService.guardar(rubro);
            redirectAttributes.addFlashAttribute("mensaje", "El rubro de empresa se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/rubros";

        } catch (Exception e) {
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("mensaje", "Ocurrió un error al guardar el rubro de empresa.");
            return "rubros/guardar";
        }
    }

    // Editar rubro -----------------------------------------------------
    @GetMapping("/editar/{id}")
    public String editarRubro(@PathVariable Integer id, Model model) {
        Rubro rubro = rubroService.obtenerPorId(id);

        if (rubro == null) {
            return "redirect:/rubros";
        }

        model.addAttribute("rubro", rubro);
        return "rubros/guardar";
    }

    @PostMapping("/editar/{id}")
    public String actualizarRubro(@PathVariable Integer id, @ModelAttribute Rubro rubro,
            RedirectAttributes redirectAttributes, Model model) {
        Rubro rubroExiste = rubroService.obtenerPorId(id);

        if (rubroExiste == null) {
            return "redirect:/rubros";
        }
        try {
            rubroExiste.setDescripcion(rubro.getDescripcion());
            rubroExiste.setFechaMod(LocalDateTime.now());
            rubroService.guardar(rubroExiste);
            redirectAttributes.addFlashAttribute("mensaje", "El rubro de empresa se editó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/rubros";

        } catch (Exception e) {
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("mensaje", "Ocurrió un error al editar el rubro de empresa.");
            return "rubros/guardar";
        }

    }

    // Eliminar rubro -----------------------------------------------------
    @GetMapping("/eliminar/{id}")
    public String eliminarRubro(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Rubro rubro = rubroService.obtenerPorId(id);

        if (rubro != null) {
            rubroService.eliminar(id);
        }
        redirectAttributes.addFlashAttribute("mensaje", "El estado del rubro de empresa se eliminó correctamente.");
        redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        return "redirect:/rubros";
    }

    // Cambiar estado del rubro -------------------------------------------
    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Rubro rubro = rubroService.obtenerPorId(id);

        if (rubro != null) {
            if (rubro.getEstado().equals("A")) {
                rubro.setEstado("I");
            } else {
                rubro.setEstado("A");
            }

            rubroService.guardar(rubro);
            redirectAttributes.addFlashAttribute("mensaje",
                    "El estado del rubro de empresa se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        }

        return "redirect:/rubros";
    }
}
