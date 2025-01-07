package com.industrial.pasantias.Model.PK;

import com.industrial.pasantias.Model.EmpresaPrograma;
import com.industrial.pasantias.Model.Pasantia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasantiaProgramaPK {
    @ManyToOne
    @JoinColumn(name = "ID_PASANTIA", nullable = false)
    private Pasantia pasantia;

    @ManyToOne
    @JoinColumn(name = "ID_PROGRAMA", nullable = false)
    private EmpresaPrograma programa;
}
