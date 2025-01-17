package com.industrial.pasantias.Model;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "LOG_CAMBIO_CARRERA", schema = "dbo")
public class CambioDeCarrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID_LOG;
    private String CARNET;

    private String ESTADO;

    @ManyToOne
    @JoinColumn(name = "CARRERA_ACTUAL", referencedColumnName = "ID_CARRERA", nullable = false)
    private Carrera CARRERA_ACTUAL;

    @CreationTimestamp
    private Date FECHA_CREA;

    @UpdateTimestamp
    private Date FECHA_MOD;

}
