package com.industrial.pasantias.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Servicio.EmpresaService;
import com.industrial.pasantias.Servicio.RubroService;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final RubroService rubroService;

    public EmpresaController(EmpresaService empresaService, RubroService rubroService) {
        this.empresaService = empresaService;
        this.rubroService = rubroService;
    }

    // Mostrar todas las empresas
    @GetMapping
    public String listarEmpresas(Model model) {
        model.addAttribute("empresas", empresaService.listarEmpresas());
        return "empresas/listado_empresas";
    }

    // Mostrar formulario nueva empresa
    @GetMapping("/nueva")
    public String nuevaEmpresaForm(Model model) {
        model.addAttribute("empresa", new Empresa());
        model.addAttribute("rubro", rubroService.obtenerTodos());
        return "empresas/crear_editar_empresa";
    }

    // Guardar nueva empresa
    @PostMapping("/guardar")
    public String guardarEmpresa(@ModelAttribute("empresa") Empresa empresa, RedirectAttributes redirectAttributes,
            Model model) {
        try {
            empresaService.guardar(empresa);
            redirectAttributes.addFlashAttribute("mensaje", "La empresa se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/empresas";
        } catch (Exception e) {
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("mensaje", "Ocurrió un error al guardar la empresa.");
            return "empresas/crear_editar_empresa";
        }
    }

    // Mostrar formulario editar empresa
    @GetMapping("/editar/{id}")
    public String mostrarFormularioModificarEmpresa(@PathVariable Integer id, Model model) {
        Empresa empresa = empresaService.obtenerPorId(id);
        if (empresa == null) {
            return "redirect:/empresas";
        }
        model.addAttribute("empresa", empresa);
        model.addAttribute("rubro", rubroService.obtenerTodos());
        return "empresas/crear_editar_empresa";
    }

    // Actualizar empresa
    @PostMapping("/editar/{id}")
    public String actualizarEmpresa(@PathVariable Integer id, @ModelAttribute Empresa empresa,
            RedirectAttributes redirectAttributes, Model model) {
        try {
            Empresa empresaExistente = empresaService.obtenerPorId(id);
            if (empresa == null) {
                return "redirect:/empresas";
            }

            empresaExistente.setNombre(empresa.getNombre());
            empresaExistente.setEstado(empresa.getEstado());
            empresaExistente.setRubro(empresa.getRubro());
            empresaExistente.setUbicacion(empresa.getUbicacion());
            empresaExistente.setTelefono(empresa.getTelefono());
            empresaExistente.setSitioWeb(empresa.getSitioWeb());

            empresaService.guardar(empresaExistente);
            redirectAttributes.addFlashAttribute("mensaje", "La empresa se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/empresas";

        } catch (Exception e) {
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("mensaje", "Ocurrió un error al actualizar la empresa.");
            return "empresas/crear_editar_empresa";
        }
    }

    // Eliminar empresa
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            empresaService.eliminarEmpresa(id);
            redirectAttributes.addFlashAttribute("mensaje", "La empresa se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar la empresa.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/empresas";
    }

}
