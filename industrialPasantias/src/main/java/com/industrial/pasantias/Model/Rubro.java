package com.industrial.pasantias.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rubro {
    @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_RUBRO") 
  private Long idRubro;
   private String descripcion;
   private String estado;
    @Column(name = "FECHA_CREA", updatable = false)
    private LocalDateTime fechaCrea;

    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;
}
