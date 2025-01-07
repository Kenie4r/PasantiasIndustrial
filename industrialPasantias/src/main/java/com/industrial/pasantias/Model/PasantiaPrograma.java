package com.industrial.pasantias.Model;

import java.time.LocalDateTime;

import com.industrial.pasantias.Model.PK.PasantiaProgramaPK;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PASANTIA_PROGRAMA")
public class PasantiaPrograma {
    @EmbeddedId
    private PasantiaProgramaPK id;

    @Column(name = "MATERIAS_CURSADAS_CANTIDAD", nullable = false)
    private Integer materiasCursadasCantidad;

    @Column(name = "MATERIAS_CURSADAS_UV", nullable = false)
    private Integer materiasCursadasUv;

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
