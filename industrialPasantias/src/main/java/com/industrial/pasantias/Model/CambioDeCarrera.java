package com.industrial.pasantias.Model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "LOG_CAMBIO_CARRERA", schema = "dbo")
public class CambioDeCarrera {

    private int ID_LOG;
    private String CARNET;

    private String ESTADO;

    private Carrera CARRERA_ACTUAL;
    private Carrera CARRERA_ANTIGUA;


    private Date FECHA_CAMBIO;


}
