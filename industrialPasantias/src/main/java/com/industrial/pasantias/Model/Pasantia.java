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
@Table(name = "PASANTIA")
public class Pasantia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PASANTIA")
    private Integer idPasantia;

    @Column(name = "COD_PASANTE", nullable = false)
    private String codPasante;

    @Column(name = "ID_PROGRAMA", nullable = false)
    private Integer idPrograma;

    @Column(name = "FECHA_INICIO", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "FECHA_FIN", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "FECHA_CREA", nullable = false)
    private LocalDateTime fechaCrea;

    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;

    @Column(name = "USU_CREA", nullable = false)
    private String usuCrea;

    @Column(name = "USU_MOD")
    private String usuMod;
}
