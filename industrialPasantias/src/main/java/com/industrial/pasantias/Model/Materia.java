package com.industrial.pasantias.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder.In;
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
    private int idMateria;

    @Column(name = "NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "ESTADO", nullable = false, length = 1)
    private String estado;

    @Column(name = "ID_CARRERA", nullable = false)
    private int idCarrera;

    @Column(name = "HORAS", nullable = true, length = 250)
    private int horas;

   

    @Column(name = "FECHA_CREA")
    private LocalDateTime fechaCrea;

    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;
    @Column(name = "USU_CREA")
    private String usuarioCrea;

    @Column(name = "USU_MOD")
    private String usuarioMod;
    @PrePersist
    protected void onCreate() {
        this.fechaCrea = LocalDateTime.now();
        this.fechaMod = LocalDateTime.now();
        this.usuarioCrea="A";
        this.usuarioMod="A";


    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaMod = LocalDateTime.now();
       
        this.usuarioMod="A";
    }
}
