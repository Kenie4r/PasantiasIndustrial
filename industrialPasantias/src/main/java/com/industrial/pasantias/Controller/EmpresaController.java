package com.industrial.pasantias.Controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Servicio.EmpresaService;
import com.industrial.pasantias.Servicio.RubroService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;
    private final RubroService rubroService;
    public EmpresaController(EmpresaService empresaService,RubroService rubroService) {
        this.empresaService = empresaService;
        this.rubroService=rubroService;
    }

    @GetMapping
    public String listarEmpresas(Model model) {
        model.addAttribute("empresas", empresaService.listarEmpresas());
        return "empresas"; // Vista HTML llamada "empresas.html"
    }
    @GetMapping("/nueva")
    public String nuevaEmpresaForm(Model model) {
        model.addAttribute("empresa", new Empresa());
        model.addAttribute("rubro", rubroService.obtenerTodos());
        return "nueva";
    }
    @PostMapping("/guardar")
     public String guardarEmpresa(@ModelAttribute("empresa") Empresa empresa) {
       
        empresaService.crearEmpresa(empresa);  // Guardar o actualizar
        return "redirect:/empresas";  // Redirigir al listado
    }
    @PostMapping("/editar")
    public String editarEmpresa(Empresa empresa) {
        empresaService.actualizarEmpresa(empresa);
        return "redirect:/empresas"; // Redirige de nuevo a la lista
    }
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("idEmpresa") int idEmpresa) {
        empresaService.eliminarEmpresa(idEmpresa);
        return "redirect:/empresas"; // Redirige al listado de empresas
    }
    
}
