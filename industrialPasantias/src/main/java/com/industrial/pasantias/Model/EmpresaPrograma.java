package com.industrial.pasantias.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROGRAMA")
public class EmpresaPrograma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROGRAMA")
    private Integer idPrograma;

    @Column(name = "NOMBRE_PROGRAMA", nullable = false, length = 200)
    private String nombrePrograma;

    @Column(name = "NOMBRE_RESPONSABLE", nullable = false, length = 200)
    private String nombreResponsable;

    @Column(name = "AREA_REALIZACION", nullable = false, length = 200)
    private String areaRealizacion;

    @Column(name = "OTROS_DETALLES", nullable = false, length = 200)
    private String otrosDetalles;

    @Column(name = "OBSERVACIONES", nullable = false, length = 200)
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "ID_MATERIA", nullable = false)
    private Materia materia;

    @Column(name = "TIPO_PROGRAMA", nullable = false, length = 200)
    private String tipoPrograma;

    @Column(name = "ESTADO", nullable = false, length = 200)
    private String estado;

    @Column(name = "FECHA_INI")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaIni;

    @Column(name = "FECHA_FIN")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFi;
    
    @Column(name = "FECHA_CREA")
    private LocalDateTime fechaCrea;

    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;

    @PrePersist
    protected void onCreate() {
        this.fechaCrea = LocalDateTime.now();
        this.fechaMod = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaMod = LocalDateTime.now();
    }
}
