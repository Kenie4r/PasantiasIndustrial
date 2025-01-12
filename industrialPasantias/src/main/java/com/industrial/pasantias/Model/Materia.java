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
@Table(name = "MATERIA")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MATERIA")
    private Integer idMateria;

    @Column(name = "NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "ESTADO", nullable = false, length = 1)
    private String estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CARRERA", referencedColumnName = "ID_CARRERA", nullable = false)
    private Carrera carrera;

    @Column(name = "HORAS", nullable = true, length = 250)
    private Integer horas;

    @CreationTimestamp
    @Column(name = "FECHA_CREA", nullable = false)
    private LocalDateTime fechaCrea;

    @UpdateTimestamp
    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;

    @Column(name = "USU_CREA", nullable = false)
    private String usuarioCrea;

    @Column(name = "USU_MOD")
    private String usuarioMod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PENSUM", referencedColumnName = "ID_PENSUM", nullable = false)
    private Pensum pensum;
}
