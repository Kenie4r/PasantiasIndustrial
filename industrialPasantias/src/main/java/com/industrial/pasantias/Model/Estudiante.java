package com.industrial.pasantias.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ESTUDIANTE")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CARNET")
    private String carnet;

    @Column(name = "NOMBRES", nullable = false)
    private String nombres;

    @Column(name = "APELLIDOS", nullable = false)
    private String apellidos;

    @Column(name = "TELEFONO", nullable = false)    
    private String telefono;

    @Column(name = "TELEFONO2")
    private String telefono2;

    @Column(name = "CORREO", nullable = false)
    private String correo;

    @Column(name = "HOLA_DE_VIDA", nullable = false)
    private String hojaDeVida;

    @Column(name = "ID_CARRERA", nullable = false)
    private Integer idCarrera;

    @Column(name = "FOTO_URL", nullable = false)
    private String fotoUrl;

    @Column(name = "FECHA_CREA", nullable = false)
    private LocalDateTime fechaCre;

    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;

    // Constructor con parámetros
    public Estudiante(String carnet, String nombres, String apellidos, String telefono, String correo, String hojaDeVida, Integer idCarrera, String fotoUrl, LocalDateTime fechaCre) {
        this.carnet = carnet;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.hojaDeVida = hojaDeVida;
        this.idCarrera = idCarrera;
        this.fotoUrl = fotoUrl;
        this.fechaCre = fechaCre;
    }
}
