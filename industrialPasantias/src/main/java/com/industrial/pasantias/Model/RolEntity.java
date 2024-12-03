package com.industrial.pasantias.Model;

import java.sql.Date;

import org.springframework.boot.context.properties.bind.Name;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ROL", schema = "dbo")
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_ROL;

    private String DESCRIPCION;

    private String ESTADO;

    private Date FECHA_CREA;

    private Date FECHA_MOD;

}
