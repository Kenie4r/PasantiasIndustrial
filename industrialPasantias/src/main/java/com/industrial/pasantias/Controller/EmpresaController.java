package com.industrial.pasantias.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Servicio.EmpresaService;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    // Listar empresas
    @GetMapping
    public String listEmpresas(Model model) {
        model.addAttribute("empresas", empresaService.findAll());
        return "empresas/list";
    }

    // Mostrar formulario para crear una nueva empresa
    @GetMapping("/create")
    public String createEmpresaForm(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }

    // Guardar una empresa (nueva o existente)
    @PostMapping
    public String saveEmpresa(@ModelAttribute Empresa empresa) {
        empresaService.save(empresa);
        return "redirect:/empresas";
    }

    // Mostrar formulario para editar una empresa existente
    @GetMapping("/edit/{id}")
    public String editEmpresaForm(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.findById(id);
        model.addAttribute("empresa", empresa);
        return "empresas/form";
    }

    // Eliminar una empresa
    @GetMapping("/delete/{id}")
    public String deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteById(id);
        return "redirect:/empresas";
    }
}
