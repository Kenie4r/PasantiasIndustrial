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
        this.usuarioCrea = "A"; // PENDIENTE
        this.usuarioMod = "A"; // PENDIENTE
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaMod = LocalDateTime.now();
        this.usuarioMod = "A"; // PENDIENTE
    }

}
