package com.industrial.pasantias.Model;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RUBRO_EMPRESA")
public class Rubro {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RUBRO")
    private Integer idRubro;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "FECHA_CREA", nullable = false)
    private LocalDateTime fechaCrea;

    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;

    //Constructor
    public Rubro(String descripcion, String estado, LocalDateTime fechaCrea) {
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCrea = fechaCrea;
    }
}
