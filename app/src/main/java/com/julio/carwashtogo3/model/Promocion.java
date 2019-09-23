package com.julio.carwashtogo3.model;

import java.util.List;

public class Promocion {
    private String UId;

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    private String nombre;
    private String encargado;
    private String precio;
    private String fechaIncio;
    private String fechaFinal;

    private List<String> extras;
    private String descripcionPromo;
    private String urlImagen;

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Promocion() {
    }

    public Promocion(String nombre, String encargado, String precio, String fechaIncio, String fechaFinal, List<String> extras, String descripcionPromo, String urlImagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.fechaIncio = fechaIncio;
        this.fechaFinal = fechaFinal;
        this.extras = extras;
        this.descripcionPromo = descripcionPromo;
        this.urlImagen = urlImagen;
        this.encargado = encargado;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFechaIncio() {
        return fechaIncio;
    }

    public void setFechaIncio(String fechaIncio) {
        this.fechaIncio = fechaIncio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public List<String> getExtras() {
        return extras;
    }

    public void setExtras(List<String> extras) {
        this.extras = extras;
    }

    public String getDescripcionPromo() {
        return descripcionPromo;
    }

    public void setDescripcionPromo(String descripcionPromo) {
        this.descripcionPromo = descripcionPromo;
    }
}
