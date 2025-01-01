package com.industrial.pasantias.Controller;

import java.io.File;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
        return "estudiante/crear_editar_estudiante";
    }

    @PostMapping("/crear")
    public String guardarEstudiante(@ModelAttribute EstudianteEntity estudiante,
            @RequestParam("HojaDeVida") MultipartFile hojaDeVida,
            @RequestParam("FotoUrl") MultipartFile fotoUrl, RedirectAttributes redirectAttributes) {
        try {

            HashMap<String, String> rutas = rutasDeDestino(fotoUrl, hojaDeVida);
            estudiante.setFOTO_URL("");
            estudiante.setHOJA_DE_VIDA("");

            if (rutas.containsKey("rutaFoto")) {
                estudiante.setFOTO_URL(rutas.get("rutaFoto"));
            }

            if (rutas.containsKey("rutaCV")) {
                estudiante.setHOJA_DE_VIDA(rutas.get("rutaCV"));
            }

            estudiante.setID_CARRERA(1); //PENDIENTE
            estudiante.setFECHA_CREA(new Date(System.currentTimeMillis()));
            Optional<EstudianteEntity> response = service.crearEstudiante(estudiante);
            if (!response.isPresent()) {
                logger.info("No se pudo guardar el estudiante.");
            }
            redirectAttributes.addFlashAttribute("mensaje", "El estudiante se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el estudiante.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/estudiantes/obtenerTodos";
    }

    public HashMap<String, String> rutasDeDestino(MultipartFile fotoUrl, MultipartFile hojaDeVida) {
        HashMap<String, String> rutas = new HashMap<>();
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");
            String contentTypeCV = hojaDeVida.getContentType();
            String contentTypeFU = fotoUrl.getContentType();
            String nameFileCV = "";
            String nameFileFU = "";

            System.out.println(contentTypeCV);
            System.out.println(contentTypeFU);

            if (contentTypeCV != null) {
                String[] contentTypeCVS = contentTypeCV.split("/");

                boolean noFile = (contentTypeCV.equals("application/octet-stream") ? false : true);

                if (noFile) {
                    nameFileCV = String.format("%s_CV.%s", format.format(LocalDateTime.now()), contentTypeCVS[1]);

                    String destinyRouteCV = String.format("%s%s%s%s%s",
                            environment.getProperty("route.destiny.files", String.class),
                            File.separator,
                            "CV",
                            File.separator,
                            nameFileCV);

                    hojaDeVida.transferTo(new File(destinyRouteCV));
                    rutas.put("rutaCV", destinyRouteCV);
                }
            }

            if (contentTypeFU != null) {

                boolean noFile = (contentTypeFU.equals("application/octet-stream") ? false : true);

                if (noFile) {
                    String[] contentTypeFUS = contentTypeFU.split("/");

                    nameFileFU = String.format("%s_FU.%s", format.format(LocalDateTime.now()), contentTypeFUS[1]);

                    String destinyRouteFU = String.format("%s%s%s%s%s",
                            environment.getProperty("route.destiny.files", String.class),
                            File.separator,
                            "fotos",
                            File.separator,
                            nameFileFU);

                    fotoUrl.transferTo(new File(destinyRouteFU));
                    rutas.put("rutaFoto", destinyRouteFU);
                }
            }
            return rutas;
        } catch (Exception e) {
            logger.error("Error al obtener las rutas de los archivos: {}", e.getMessage());
            return rutas;
        }

    }

    @PostMapping("/editar/{carnet}")
    public String editarEstudiante(@PathVariable String carnet, RedirectAttributes redirectAttributes,
            @ModelAttribute EstudianteEntity estudiante, @RequestParam("HojaDeVida") MultipartFile hojaDeVida,
            @RequestParam("FotoUrl") MultipartFile fotoUrl) {
        try {
            Optional<EstudianteEntity> optional = service.obtenerDataModificar(carnet);
            if (optional.isPresent()) {

                EstudianteEntity estudianteExistente = optional.orElse(new EstudianteEntity());
                estudianteExistente.setHOJA_DE_VIDA(estudianteExistente.getHOJA_DE_VIDA());
                estudianteExistente.setFOTO_URL(estudianteExistente.getFOTO_URL());

                HashMap<String, String> rutas = rutasDeDestino(fotoUrl, hojaDeVida);

                if (rutas.containsKey("rutaFoto")) {
                    estudianteExistente.setFOTO_URL(rutas.get("rutaFoto"));
                }

                if (rutas.containsKey("rutaCV")) {
                    estudianteExistente.setHOJA_DE_VIDA(rutas.get("rutaCV"));
                }

                estudianteExistente.setCarnet(estudiante.getCarnet());
                estudianteExistente.setID_CARRERA(1); //PENDIENTE
                estudianteExistente.setFECHA_CREA(estudianteExistente.getFECHA_CREA());
                estudianteExistente.setCORREO(estudiante.getCORREO());
                estudianteExistente.setAPELLIDOS(estudiante.getAPELLIDOS());
                estudianteExistente.setFECHA_MOD(new Date(System.currentTimeMillis()));
                estudianteExistente.setNOMBRES(estudiante.getNOMBRES());
                estudianteExistente.setTELEFONO(estudiante.getTELEFONO());
                estudianteExistente.setTELEFONO2(estudiante.getTELEFONO2());

                service.modificarEstudiante(estudianteExistente);
                redirectAttributes.addFlashAttribute("mensaje", "El estudiante se editó correctamente.");
                redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            }else{
                System.out.println("NO HAY OPCIONAL");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el estudiante.");
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
            return "estudiante/crear_editar_estudiante";
        } catch (Exception e) {
            return "redirect:/estudiantes/obtenerTodos";
        }
    }

    @GetMapping("/eliminar/{carnet}")
    public String eliminarEstudiante(@PathVariable String carnet, RedirectAttributes redirectAttributes) {
        try {
            service.eliminarEstudiante(carnet);
            redirectAttributes.addFlashAttribute("mensaje", "El estudiante se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el estudiante.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/estudiantes/obtenerTodos";
    }
}
