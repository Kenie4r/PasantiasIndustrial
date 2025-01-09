package com.industrial.pasantias.Servlets;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.industrial.pasantias.Servicio.EstudianteService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import javax.sql.DataSource;

@Controller
public class reportPrinter {
    @Autowired
    private DataSource dataSource; 

    @Autowired
    private EstudianteService restudianteService; 

    @GetMapping("/reportPrinter")
    @ResponseBody
    public void generarReportePDF(HttpServletResponse response, 
                                  @RequestParam(required = false) String parametroEjemplo) {
        try {
            // Ruta del archivo .jasper en el directorio de recursos
            InputStream jasperStream = getClass().getResourceAsStream("/static/reports/prueba.jasper");

            if (jasperStream == null) {
                throw new Exception("No se pudo encontrar el archivo de reporte.");
            }

            // Cargar el reporte Jasper
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

            // Parámetros del reporte (puedes agregar más según tu reporte)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("columnCount", 5); 

            // Obtener la conexión de la base de datos desde el datasource de Spring
            try (Connection conn = dataSource.getConnection()) {

                // Llenar el reporte con los parámetros y la conexión
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

                // Establecer las cabeceras de la respuesta HTTP
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=\"reporte.pdf\"");

                // Exportar el reporte a PDF y escribir en la respuesta
                JRPdfExporter exporter = new JRPdfExporter();
                 exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream())); // Aquí usamos SimpleOutputStreamExporterOutput
                exporter.exportReport();
            }
        } catch (JRException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/compileReport/{nombre}")
    @ResponseBody
    public void getMethodName( @PathVariable String nombre) {
        try {
            InputStream employeeReportStream = getClass().getResourceAsStream("/static/reports/"+nombre+".jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
            JRSaver.saveObject(jasperReport, nombre + ".jasper");
        } catch(Exception ex){
            ex.printStackTrace();
        }

    }
    


    @GetMapping("/formularios/{nombre}/{pasantia}")
    @ResponseBody
    public void generarReportePDFparams(HttpServletResponse response, 
                                  @RequestParam(required = false) String parametroEjemplo, @PathVariable String nombre, @PathVariable String pasantia) {
        try {
            String reportName = "FI01"; 
            String[] datosPasantia = pasantia.split("-"); 
            // Ruta del archivo .jasper en el directorio de recursos
            InputStream jasperStream = getClass().getResourceAsStream("/static/reports/"+ nombre + ".jasper");

            if (jasperStream == null) {
                throw new Exception("No se pudo encontrar el archivo de reporte.");
            }

            // Cargar el reporte Jasper
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

            // Parámetros del reporte (puedes agregar más según tu reporte)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ID_PASANTIA", datosPasantia[0]); 
            parameters.put("ID_CARRERA", datosPasantia[1]); 
            parameters.put("CARNET", datosPasantia[2]); 
            parameters.put(JRParameter.REPORT_MAX_COUNT, 1);

            // Obtener la conexión de la base de datos desde el datasource de Spring
            try (Connection conn = dataSource.getConnection()) {

                // Llenar el reporte con los parámetros y la conexión
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);


                //Obtenemo el nombre del estudiante
                reportName += restudianteService.obtenerDataModificar( datosPasantia[2]).get().getAPELLIDOS(); 

                // Establecer las cabeceras de la respuesta HTTP
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=\""+reportName+ ".pdf\"");

                // Exportar el reporte a PDF y escribir en la respuesta
                JRPdfExporter exporter = new JRPdfExporter();
                 exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream())); // Aquí usamos SimpleOutputStreamExporterOutput
                exporter.exportReport();
            }
        } catch (JRException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
