package com.industrial.pasantias.Model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CARRERA", schema = "dbo")
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARRERA")
    private Integer idCarrera;
    
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "ESTADO", nullable = false, length = 1)
    private String estado;

    @Column(name = "FECHA_CREA", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaCre;

    @Column(name = "FECHA_MOD")
    @UpdateTimestamp
    private LocalDateTime fechaMod;
    
    @Column(name = "USU_CREA", nullable = false, length = 50, updatable = false)
    private String usuCrea;

    @Column(name = "USU_MOD", length = 50)
    private String usuMod;

    @Column(name = "COD_CARRERA", unique = true)
    private Integer codCarrera;

    // Constructor con parámetros
    public Carrera(String descripcion, String estado, LocalDateTime fechaCre, String usuCrea, Integer codCarrera) {
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCre = fechaCre;
        this.usuCrea = usuCrea;
        this.codCarrera = codCarrera;
    }
}
