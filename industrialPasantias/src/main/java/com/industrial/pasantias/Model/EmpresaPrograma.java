package com.industrial.pasantias.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPRESA_PROGRAMA")
public class EmpresaPrograma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROGRAMA")
    private int idPrograma;
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

    @Column(name = "ESTADO", nullable = false, length = 200)
    private String estado;
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
