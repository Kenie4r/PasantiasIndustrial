package com.industrial.pasantias.Model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PASANTIA")
public class Pasantia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PASANTIA")
    private Integer idPasantia;

    @ManyToOne
    @JoinColumn(name = "ID_CARRERA", nullable = false)
    private Carrera carrera;

    @Column(name = "CORRELATIVO", nullable = false)
    private Integer correlativo;

    @Column(name = "ANIO_ESTUDIANTE", nullable = false)
    private String anioEstudiante;

    @ManyToOne
    @JoinColumn(name = "CARNET", nullable = false)
    private EstudianteEntity estudiante;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @CreationTimestamp
    @Column(name = "FECHA_CREA", nullable = false)
    private LocalDateTime fechaCrea;

    @UpdateTimestamp
    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;

    @Column(name = "USU_CREA", nullable = false)
    private String usuCrea;

    @Column(name = "USU_MOD")
    private String usuMod;

    @Column(name = "FECHA_INGRESO_UNIVERSIDAD", nullable = false)
    private LocalDateTime fechaIngresoUniversidad;
}
