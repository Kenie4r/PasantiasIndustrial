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
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "NOMBRES", nullable = false)
    private String nombres;

    @Column(name = "APELLIDOS", nullable = false)
    private String apellidos;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASS", nullable = false)
    private String password;

    @Column(name = "CORREO", nullable = false)
    private String correo;

    @ManyToOne
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL", nullable = false)
    private RolEntity idRol;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @CreationTimestamp
    @Column(name = "FECHA_CREA", nullable = false)
    private LocalDateTime fechaCrea;

    @UpdateTimestamp
    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;

    // Constructor con parámetros
    public Usuario(String nombres, String apellidos, String username, String password, String correo, RolEntity idRol,
            String estado, LocalDateTime fechaCrea) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.correo = correo;
        this.idRol = idRol;
        this.estado = estado;
        this.fechaCrea = fechaCrea;
    }
}
