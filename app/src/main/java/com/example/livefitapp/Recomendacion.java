package com.example.livefitapp;

public class Recomendacion {
    String nombre, descripcion, image;

    public Recomendacion() {
    }

    public Recomendacion(String nombre, String descripcion, String image) {
        this.nombre = nombre;
        this.image = image;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
