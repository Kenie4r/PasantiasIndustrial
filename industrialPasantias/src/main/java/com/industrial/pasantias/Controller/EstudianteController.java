package com.industrial.pasantias.Controller;

import java.io.File;
import java.lang.reflect.Array;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.industrial.pasantias.Model.EstudianteEntity;
import com.industrial.pasantias.Servicio.EstudianteService;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService service;

    @Autowired
    private Environment environment;

    private static Logger logger = LoggerFactory.getLogger(EstudianteController.class);
    @GetMapping("/obtenerTodos")
    public String listarTodos(Model model) {

        Optional<List<EstudianteEntity>> roles = service.obtenerTodos();

        if (roles.isPresent()) {
            model.addAttribute("estudiantes", roles.orElse(new ArrayList<>()));
        }

        if (!roles.isPresent()) {
            model.addAttribute("estudiantes", new ArrayList<>());
        }

        return "estudiante/listado_estudiantes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioEstudiante(Model model) {
        model.addAttribute("estudiante", new EstudianteEntity());

        return "estudiante/creareditarestudiante";
    }

    @PostMapping("/crear")
    public String guardarEstudiante(@ModelAttribute EstudianteEntity estudiante,
            @RequestParam("HojaDeVida") MultipartFile hojaDeVida,
            @RequestParam("FotoUrl") MultipartFile fotoUrl, RedirectAttributes redirectAttributes) {
        try {

            DateTimeFormatter format = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");

            String contentTypeCV = hojaDeVida.getContentType();
            String contentTypeFU = fotoUrl.getContentType();
            String nameFileCV = "";
            String nameFileFU = "";

            if(contentTypeCV != null && contentTypeFU != null){
                String[] contentTypeCVS = contentTypeCV.split("/") ;
                String[] contentTypeFUS = contentTypeFU.split("/");
                nameFileCV = String.format("%s_CV.%s",format.format(LocalDateTime.now()), contentTypeCVS[1]);

                nameFileFU = String.format("%s_FU.%s",format.format(LocalDateTime.now()), contentTypeFUS[1]);
            }

            //String nameFileCV = hojaDeVida.getOriginalFilename(); 

            if (nameFileCV != null && !nameFileCV.isEmpty()) {
                String destinyRouteCV = String.format("%s%s%s%s%s",
                        environment.getProperty("route.destiny.files", String.class),
                        File.separator,
                        "CV",
                        File.separator,
                        nameFileCV);

                hojaDeVida.transferTo(new File(destinyRouteCV));

                estudiante.setHOJA_DE_VIDA(destinyRouteCV);
            }

            if (nameFileFU != null && !nameFileFU.isEmpty()) {
                String destinyRouteFU = String.format("%s%s%s%s%s",
                        environment.getProperty("route.destiny.files", String.class),
                        File.separator,
                        "fotos",
                        File.separator,
                        nameFileFU);

                fotoUrl.transferTo(new File(destinyRouteFU));
                estudiante.setFOTO_URL(destinyRouteFU);
            }

            estudiante.setID_CARRERA(1);
            estudiante.setFECHA_CREA(new Date(System.currentTimeMillis()));

            Optional<EstudianteEntity> response  = service.crearEstudiante(estudiante);

            if(!response.isPresent()){
                logger.info("No se pudo guardar el estudiante.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
            
        }
        return "redirect:/estudiantes/obtenerTodos";
    }

    @PostMapping("/editar/{carnet}")
    public String editarEstudiante(@PathVariable String carnet, RedirectAttributes redirectAttributes,
            @ModelAttribute EstudianteEntity estudiante, @RequestParam("HojaDeVida") MultipartFile hojaDeVida,
            @RequestParam("FotoUrl") MultipartFile fotoUrl) {
        try {

            Optional<EstudianteEntity> optional = service.obtenerDataModificar(carnet);

            if (optional.isPresent()) {

                System.out.print("Modificando...");
            
                EstudianteEntity estudianteExistente = optional.orElse(new EstudianteEntity());

        
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");

                String contentTypeCV = hojaDeVida.getContentType();
                String contentTypeFU = fotoUrl.getContentType();
                String nameFileCV = "";
                String nameFileFU = "";
    
               
                estudianteExistente.setHOJA_DE_VIDA(estudianteExistente.getHOJA_DE_VIDA());
                estudianteExistente.setFOTO_URL(estudianteExistente.getFOTO_URL());
                
                if(contentTypeCV != null && contentTypeFU != null){
                    String[] contentTypeCVS = contentTypeCV.split("/") ;
                    String[] contentTypeFUS = contentTypeFU.split("/");
                    nameFileCV = String.format("%s_CV.%s",format.format(LocalDateTime.now()), contentTypeCVS[1]);
    
                    nameFileFU = String.format("%s_FU.%s",format.format(LocalDateTime.now()), contentTypeFUS[1]);
                }
    
                //String nameFileCV = hojaDeVida.getOriginalFilename(); 
    
                if (nameFileCV != null && !nameFileCV.isEmpty()) {
                    String destinyRouteCV = String.format("%s%s%s%s%s",
                            environment.getProperty("route.destiny.files", String.class),
                            File.separator,
                            "CV",
                            File.separator,
                            nameFileCV);
    
                    hojaDeVida.transferTo(new File(destinyRouteCV));
    
                    estudianteExistente.setHOJA_DE_VIDA(destinyRouteCV);
                }
    
                if (nameFileFU != null && !nameFileFU.isEmpty()) {
                    String destinyRouteFU = String.format("%s%s%s%s%s",
                            environment.getProperty("route.destiny.files", String.class),
                            File.separator,
                            "fotos",
                            File.separator,
                            nameFileFU);
    
                    fotoUrl.transferTo(new File(destinyRouteFU));
                    estudianteExistente.setFOTO_URL(destinyRouteFU);
                }

                System.out.println(estudianteExistente.getFOTO_URL());
                estudianteExistente.setCarnet(estudiante.getCarnet());
                estudianteExistente.setID_CARRERA(1);
    
                estudianteExistente.setFECHA_CREA(estudianteExistente.getFECHA_CREA());
                estudianteExistente.setCORREO(estudiante.getCORREO());
                estudianteExistente.setAPELLIDOS(estudiante.getAPELLIDOS());
                estudianteExistente.setFECHA_MOD(new Date(System.currentTimeMillis()));
                estudianteExistente.setNOMBRES(estudiante.getNOMBRES());
                estudianteExistente.setTELEFONO(estudiante.getTELEFONO());
                estudianteExistente.setTELEFONO2(estudiante.getTELEFONO2());

                service.modificarEstudiante(estudianteExistente);
                return "redirect:/estudiantes/obtenerTodos";
            }
            redirectAttributes.addFlashAttribute("mensaje", "El estudiante se edito correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/estudiantes/obtenerTodos";
    }

    @GetMapping("/editar/{carnet}")
    public String editarEstudianteForm(@PathVariable String carnet, Model model) {
        try {

            Optional<EstudianteEntity> estudiante = service.obtenerDataModificar(carnet);

            if (estudiante.isPresent()) {

                model.addAttribute("estudiante", estudiante.get());
            }

            return "estudiante/creareditarestudiante";
        } catch (Exception e) {
            return "redirect:/estudiantes/obtenerTodos";
        }

    }

    @GetMapping("/eliminar/{carnet}")
    public String eliminarEstudiante(@PathVariable String carnet, RedirectAttributes redirectAttributes) {
        try {

            System.out.println("Eliminando...");
            System.out.println(carnet);

            service.eliminarEstudiante(carnet);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        return "redirect:/estudiantes/obtenerTodos";
    }
}
