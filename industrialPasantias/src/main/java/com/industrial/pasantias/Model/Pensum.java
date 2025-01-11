package com.industrial.pasantias.Model;

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
@Table(name = "PENSUM", schema = "dbo")
public class Pensum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_PENSUM;

    private String NOMBRE;

    
}
