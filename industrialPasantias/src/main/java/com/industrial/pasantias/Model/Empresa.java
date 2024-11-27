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
@Table(name = "EMPRESA")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRESA")
    private int idEmpresa;

    @Column(name = "NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "ESTADO", nullable = false, length = 1)
    private String estado;

    @Column(name = "ID_RUBRO", nullable = false)
    private int idRubro;

    @Column(name = "UBICACION", nullable = true, length = 250)
    private String ubicacion;

    @Column(name = "TELEFONO", nullable = true, length = 20)
    private String telefono;

    @Column(name = "SITIO_WEB", nullable = true, length = 100)
    private String sitioWeb;

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
