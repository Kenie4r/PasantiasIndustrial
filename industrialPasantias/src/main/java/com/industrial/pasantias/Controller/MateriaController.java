package com.industrial.pasantias.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.industrial.pasantias.Model.Empresa;
import com.industrial.pasantias.Model.Materia;
import com.industrial.pasantias.Servicio.EmpresaService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.industrial.pasantias.Servicio.MateriaService;

@Controller
@RequestMapping("/materias")
public class MateriaController {
    private final MateriaService materia;
    public MateriaController(MateriaService materia){
        this.materia=materia;
    }
    @GetMapping
    public String listarMaterias(Model model) {
        model.addAttribute("materia", materia.listar());
        return "materia/index"; // Vista HTML llamada "empresas.html"
    }
    @GetMapping("/nueva")
    public String nuevaEmpresaForm(Model model) {
        model.addAttribute("materia", new Materia());
        return "materia/materiaNueva";
    }
    @PostMapping("/guardar")
    public String guardarEmpresa(@ModelAttribute("materia") Materia empresa) {
      
       materia.crear(empresa);  // Guardar o actualizar
       return "redirect:/materias";  // Redirigir al listado
   }
   @GetMapping("/editar/{id}")
   public String EditarEmpresaForm(Model model) {
       model.addAttribute("materia", new Materia());
       return "materia/materiaNueva";
   }
   @PostMapping("/editar")
   public String editarEmpresa(Materia empresa) {
       materia.actualizarEmpresa(empresa);
       return "redirect:/materias"; // Redirige de nuevo a la lista
   }
   
  // Eliminar un usuario
  @GetMapping("/eliminar/{id}")
  public String eliminarMateria(@PathVariable Integer id) {
      materia.eliminarMateria(id);  // Call to the service to delete the materia
      return "redirect:/materias";  // Redirect to the list of materias
  }
}
