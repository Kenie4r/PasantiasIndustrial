package com.industrial.pasantias.Model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PARAMETRO")
public class Parametro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PARAMETRO")
    private Integer idParametro;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "VALOR", nullable = false)
    private String valor;

    @Column(name = "CATEGORIA", nullable = false)
    private String categoria;

    @CreationTimestamp
    @Column(name = "FECHA_CREA", nullable = false)
    private LocalDateTime fechaCre;

    @UpdateTimestamp
    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;

    // Constructor con parámetros
    public Parametro(String descripcion, String valor, String categoria, LocalDateTime fechaCre) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.categoria = categoria;
        this.fechaCre = fechaCre;
    }
}
