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
@Table(name = "LOG_CAMBIO_CARRERA")
public class LogCarreras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOG")
    private Integer idLog;

    @Column(name = "CARNET", nullable = false, length = 200)
    private String carnet;

    @Column(name = "ESTADO", nullable = false, length = 1)
    private String estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CARRERA_ACTUAL", referencedColumnName = "ID_CARRERA", nullable = false)
    private Carrera carrera;

    /*@Column(name = "CARRERA_ACTUAL", nullable = true, length = 250)
    private Integer carreraActual;*/

    @CreationTimestamp
    @Column(name = "FECHA_CREA", nullable = false)
    private LocalDateTime fechaCrea;

    @UpdateTimestamp
    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;
    @Column(name = "PENSUM_ACTUAL", nullable = true, length = 250)
    private Integer pensumActual;
    


}
