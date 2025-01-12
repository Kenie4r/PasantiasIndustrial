package com.industrial.pasantias.Model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPRESA")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRESA")
    private Integer idEmpresa;

    @Column(name = "NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "ESTADO", nullable = false, length = 1)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RUBRO", nullable = false)
    private Rubro rubro;

    @Column(name = "UBICACION", length = 250)
    private String ubicacion;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Column(name = "SITIO_WEB", length = 100)
    private String sitioWeb;

    @Column(name = "FECHA_CREA")
    @CreationTimestamp
    private LocalDateTime fechaCrea;

    @Column(name = "FECHA_MOD")
    @UpdateTimestamp
    private LocalDateTime fechaMod;
}
