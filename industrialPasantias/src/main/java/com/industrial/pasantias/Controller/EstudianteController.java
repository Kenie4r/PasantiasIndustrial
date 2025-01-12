package com.industrial.pasantias.Controller;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.http.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
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
import org.springframework.core.io.Resource;

import com.industrial.pasantias.Model.Carrera;
import com.industrial.pasantias.Model.EstudianteEntity;
import com.industrial.pasantias.Servicio.CarreraService;
import com.industrial.pasantias.Servicio.EstudianteService;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private CarreraService carreraService;

    @Autowired
    private Environment environment;
    private static Logger logger = LoggerFactory.getLogger(EstudianteController.class);

    // Listar todos los estudiantes
    @GetMapping("/obtenerTodos")
    public String listarTodos(Model model) {
        Optional<List<EstudianteEntity>> roles = estudianteService.obtenerTodos();

        if (roles.isPresent()) {
            model.addAttribute("estudiantes", roles.orElse(new ArrayList<>()));
        }

        if (!roles.isPresent()) {
            model.addAttribute("estudiantes", new ArrayList<>());
        }
        return "estudiante/listado_estudiantes";
    }

    // Mostrar formulario crear estudiante
    @GetMapping("/nuevo")
    public String mostrarFormularioEstudiante(Model model) {
        List<Carrera> carreras = carreraService.obtenerCarrerasActivas();
        model.addAttribute("estudiante", new EstudianteEntity());

        if (carreras != null) {
            model.addAttribute("carreras", carreras);
        }

        if (carreras == null) {
            model.addAttribute("carreras", new Carrera());
        }
        return "estudiante/crear_editar_estudiante";
    }

    // Guardar estudiante
    @PostMapping("/crear")
    public String guardarEstudiante(
            @ModelAttribute EstudianteEntity estudianteEntity,
            @RequestParam(value = "HojaDeVida", required = false) MultipartFile hojaDeVida,
            @RequestParam(value = "FotoUrl", required = false) MultipartFile fotoUrl,
            RedirectAttributes redirectAttributes) {

        try {
            // Procesar rutas de destino para los archivos
            HashMap<String, String> rutas = rutasDeDestino(fotoUrl, hojaDeVida);

            estudianteEntity.setFOTO_URL(
                    rutas != null && rutas.containsKey("rutaFoto") ? rutas.get("rutaFoto") : "");
            estudianteEntity.setHOJA_DE_VIDA(
                    rutas != null && rutas.containsKey("rutaCV") ? rutas.get("rutaCV") : "");

            // Guardar en base de datos
            Optional<EstudianteEntity> response = estudianteService.crearEstudiante(estudianteEntity);

            if (response.isPresent()) {
                redirectAttributes.addFlashAttribute("mensaje", "El estudiante se guardó correctamente.");
                redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            } else {
                redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el estudiante.");
                redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            }
        } catch (Exception e) {
            logger.error("Error al guardar estudiante: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el estudiante.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/estudiantes/obtenerTodos";
    }

    // Obtener imagenes de la carpeta
    @Value("${route.destiny.files}")
    private String uploadDir;

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir + "/fotos").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("Archivo no encontrado: " + filename);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener pdfs de la carpeta
    @GetMapping("/pdfs/{filename}")
    public ResponseEntity<Resource> getCv(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir + "/cv").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("Archivo no encontrado: " + filename);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Rutas para los archivos
    public HashMap<String, String> rutasDeDestino(MultipartFile fotoUrl, MultipartFile hojaDeVida) {
        HashMap<String, String> rutas = new HashMap<>();
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");

            if (hojaDeVida != null && !hojaDeVida.isEmpty()) {
                String contentTypeCV = hojaDeVida.getContentType();
                @SuppressWarnings("null")
                String[] contentTypeCVS = contentTypeCV.split("/");
                String nameFileCV = String.format("%s_CV.%s", format.format(LocalDateTime.now()), contentTypeCVS[1]);
                String destinyRouteCV = environment.getProperty("route.destiny.files") + File.separator + "CV"
                        + File.separator + nameFileCV;

                hojaDeVida.transferTo(new File(destinyRouteCV));
                rutas.put("rutaCV", nameFileCV);
            }

            if (fotoUrl != null && !fotoUrl.isEmpty()) {
                String contentTypeFU = fotoUrl.getContentType();
                @SuppressWarnings("null")
                String[] contentTypeFUS = contentTypeFU.split("/");
                String nameFileFU = String.format("%s_FU.%s", format.format(LocalDateTime.now()), contentTypeFUS[1]);
                String destinyRouteFU = environment.getProperty("route.destiny.files") + File.separator + "fotos"
                        + File.separator + nameFileFU;

                fotoUrl.transferTo(new File(destinyRouteFU));
                rutas.put("rutaFoto", nameFileFU);
            }

        } catch (Exception e) {
            logger.error("Error al procesar archivos: {}", e.getMessage());
        }
        return rutas;
    }

    // Editar estudiante
    @PostMapping("/editar/{carnet}")
    public String editarEstudiante(
            RedirectAttributes redirectAttributes,
            @ModelAttribute EstudianteEntity estudianteEntity,
            @RequestParam(value = "HojaDeVida", required = false) MultipartFile hojaDeVida,
            @RequestParam(value = "FotoUrl", required = false) MultipartFile fotoUrl) {
        System.out.println("carnet: " + estudianteEntity.getCarnet());
        try {
            // Verificar si el estudiante existe
            estudianteService.obtenerDataModificar(estudianteEntity.getCarnet())
                    .ifPresentOrElse(estudianteExistente -> {
                        // Eliminar archivo antiguo y cargar uno nuevo si aplica
                        HashMap<String, String> rutas = new HashMap<>();

                        if (fotoUrl != null && !fotoUrl.isEmpty()) {
                            // Eliminar archivo antiguo de la foto si existía
                            if (estudianteExistente.getFOTO_URL() != null
                                    && !estudianteExistente.getFOTO_URL().isEmpty()) {
                                eliminarArchivoAntiguo(uploadDir + "/fotos/" + estudianteExistente.getFOTO_URL());
                            }
                            // Guardar nueva foto
                            rutas.putAll(rutasDeDestino(fotoUrl, null));
                        }

                        if (hojaDeVida != null && !hojaDeVida.isEmpty()) {
                            // Eliminar archivo antiguo del CV si existía
                            if (estudianteExistente.getHOJA_DE_VIDA() != null
                                    && !estudianteExistente.getHOJA_DE_VIDA().isEmpty()) {
                                eliminarArchivoAntiguo(uploadDir + "/cv/" + estudianteExistente.getHOJA_DE_VIDA());
                            }
                            // Guardar nuevo CV
                            rutas.putAll(rutasDeDestino(null, hojaDeVida));
                        }

                        // Actualizar datos del estudiante
                        if (rutas.containsKey("rutaFoto")) {
                            estudianteExistente.setFOTO_URL(rutas.get("rutaFoto"));
                        }
                        if (rutas.containsKey("rutaCV")) {
                            estudianteExistente.setHOJA_DE_VIDA(rutas.get("rutaCV"));
                        }

                        estudianteExistente.setCarrera(estudianteEntity.getCarrera());
                        estudianteExistente.setCORREO(estudianteEntity.getCORREO());
                        estudianteExistente.setAPELLIDOS(estudianteEntity.getAPELLIDOS());
                        estudianteExistente.setNOMBRES(estudianteEntity.getNOMBRES());
                        estudianteExistente.setTELEFONO(estudianteEntity.getTELEFONO());
                        estudianteExistente.setTELEFONO2(estudianteEntity.getTELEFONO2());                        

                        // Guardar cambios
                        estudianteService.modificarEstudiante(estudianteExistente);
                        redirectAttributes.addFlashAttribute("mensaje", "El estudiante se editó correctamente.");
                        redirectAttributes.addFlashAttribute("tipoMensaje", "success");

                    }, () -> {
                        redirectAttributes.addFlashAttribute("mensaje",
                                "El estudiante con el carnet proporcionado no existe.");
                        redirectAttributes.addFlashAttribute("tipoMensaje", "error");
                    });
        } catch (Exception e) {
            logger.error("Error al editar el estudiante: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el estudiante.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }

        return "redirect:/estudiantes/obtenerTodos";
    }

    // Mostrar formulario editar estudiante
    @GetMapping("/editar/{carnet}")
    public String editarEstudianteForm(@PathVariable String carnet, Model model) {
        try {

            List<Carrera> carreras = carreraService.obtenerCarrerasActivas();

            Optional<EstudianteEntity> estudiante = estudianteService.obtenerDataModificar(carnet);

            if (estudiante.isPresent()) {
                model.addAttribute("estudiante", estudiante.get());
            }

            if (carreras != null) {
                Carrera carreraSeleccionada = estudiante.get().getCarrera();
                carreras.remove(carreraSeleccionada);
                carreras.add(0, carreraSeleccionada);
                model.addAttribute("carreras", carreras);
            }

            if (carreras == null) {
                model.addAttribute("carreras", new Carrera());
            }
            return "estudiante/crear_editar_estudiante";
        } catch (Exception e) {
            return "redirect:/estudiantes/obtenerTodos";
        }
    }

    // Eliminar estudiante
    @GetMapping("/eliminar/{carnet}")
    public String eliminarEstudiante(@PathVariable String carnet, RedirectAttributes redirectAttributes) {
        try {
            EstudianteEntity estudianteEliminado = estudianteService.obtenerPorCarnet(carnet);

            if (estudianteEliminado != null) {
                // Elimina los archivos asociados (si existen)
                if (estudianteEliminado.getHOJA_DE_VIDA() != null && !estudianteEliminado.getHOJA_DE_VIDA().isEmpty()) {
                    eliminarArchivoAntiguo(uploadDir + "/cv/" + estudianteEliminado.getHOJA_DE_VIDA());
                }
                if (estudianteEliminado.getFOTO_URL() != null && !estudianteEliminado.getFOTO_URL().isEmpty()) {
                    eliminarArchivoAntiguo(uploadDir + "/fotos/" + estudianteEliminado.getFOTO_URL());
                }

                // Elimina el estudiante de la base de datos
                estudianteService.eliminar(carnet);

                redirectAttributes.addFlashAttribute("mensaje", "El estudiante se eliminó correctamente.");
                redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            } else {
                redirectAttributes.addFlashAttribute("mensaje", "El estudiante no existe.");
                redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Ocurrió un error al eliminar el estudiante." + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/estudiantes/obtenerTodos";
    }

    // Eliminar archivos antiguos
    public void eliminarArchivoAntiguo(String rutaArchivo) {
        try {
            Path filePath = Paths.get(rutaArchivo);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el archivo antiguo: {}", e.getMessage());
        }
    }
}
