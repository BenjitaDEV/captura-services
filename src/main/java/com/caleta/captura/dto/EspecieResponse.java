package com.caleta.captura.dto;

public class EspecieResponse {
    
    private Long id;
    private String nombre;
    private boolean enVeda;

    public EspecieResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEnVeda() {
        return enVeda;
    }

    public void setEnVeda(boolean enVeda) {
        this.enVeda = enVeda;
    }

    

}
