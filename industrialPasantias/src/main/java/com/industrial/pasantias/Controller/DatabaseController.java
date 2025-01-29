package com.industrial.pasantias.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.industrial.pasantias.Servicio.DatabaseService;

@Controller
@RequestMapping("/database")
public class DatabaseController {
    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/backup")
    public String backupDatabase(RedirectAttributes redirectAttributes) {
        try {
            databaseService.backupDatabase();
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            redirectAttributes.addFlashAttribute("mensaje", "Backup realizado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            redirectAttributes.addFlashAttribute("mensaje", "Error al realizar el backup: " + e.getMessage());
        }
        return "redirect:/login";
    }

    /*@PostMapping("/upload-backup")
    public ResponseEntity<String> uploadBackupFile(@RequestParam("backupFilePath") MultipartFile file) {
        try {
            // Obtener el nombre del archivo
            String fileName = file.getOriginalFilename();

            // Ruta completa del archivo (ajusta esta ruta según tu entorno)
            String backupFilePath = "C:/pasantias/backups/" + fileName;

            // Llamar al método de restauración
            databaseService.restoreDatabase(backupFilePath);
            System.out.println("backup coreccto");
            return ResponseEntity.ok("Restauración realizada con éxito desde: " + backupFilePath);
        } catch (Exception e) {
            System.err.println("Error al restaurar el backup: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Error al restaurar el backup: " + e.getMessage());
        }
    }*/

    @GetMapping("/restore-page")
    public String restorePage() {
        return "database/restore-page";
    }
}
