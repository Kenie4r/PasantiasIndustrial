package com.industrial.pasantias.Model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ESTUDIANTE", schema = "dbo")
public class EstudianteEntity {

    @Id
    @Column(name = "CARNET")
    private String carnet;

    private String NOMBRES;

    private String APELLIDOS;

    private String TELEFONO;

    private String TELEFONO2;

    private String CORREO;

    private String HOJA_DE_VIDA;

    @ManyToOne
    @JoinColumn(name = "ID_CARRERA", referencedColumnName = "ID_CARRERA")
    private Carrera Carrera;

    private String FOTO_URL;

    private Date FECHA_CREA;

    private Date FECHA_MOD;

}
