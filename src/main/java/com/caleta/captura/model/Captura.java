package com.caleta.captura.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "capturas")
public class Captura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_captura")
    private Long id;

    @Column(name = "especie_id", nullable = false)
    private Long especieId;

    @Column(nullable = false)
    private Double kilos;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    //constructores

    public Captura() {}

    public Captura(Long id, Long especieId, Double kilos, LocalDate fecha) {
        this.id = id;
        this.especieId = especieId;
        this.kilos = kilos;
        this.fecha = fecha;
    }

    //getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEspecieId() {
        return especieId;
    }

    public void setEspecieId(Long especieId) {
        this.especieId = especieId;
    }

    public Double getKilos() {
        return kilos;
    }

    public void setKilos(Double kilos) {
        this.kilos = kilos;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}
