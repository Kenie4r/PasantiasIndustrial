package com.industrial.pasantias.Model;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ESTUDIANTE", schema = "dbo")
public class EstudianteEntity {

    @Id
    @Column(name = "CARNET", nullable = false, length = 8)
    private String carnet;

    @Column(name = "NOMBRES", nullable = false, length = 100)
    private String NOMBRES;

    @Column(name = "APELLIDOS", nullable = false, length = 100)
    private String APELLIDOS;

    @Column(name = "TELEFONO", length = 9)
    private String TELEFONO;

    @Column(name = "TELEFONO2", length = 9)
    private String TELEFONO2;

    @Column(name = "CORREO", length = 125)
    private String CORREO;

    @Column(name = "HOJA_DE_VIDA")
    private String HOJA_DE_VIDA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CARRERA", referencedColumnName = "ID_CARRERA", nullable = false)
    private Carrera Carrera;

    @Column(name = "FOTO_URL")
    private String FOTO_URL;

    @CreationTimestamp
    @Column(name = "FECHA_CREA", updatable = false)
    private Date FECHA_CREA;

    @UpdateTimestamp
    @Column(name = "FECHA_MOD")
    private Date FECHA_MOD;
}
