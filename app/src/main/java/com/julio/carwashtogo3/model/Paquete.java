package com.julio.carwashtogo3.model;

public class Paquete {

    private String descripcion;
    private Double precio;
    private String titulo;
    private String Uid;
    private String urlImagen;

    public Paquete(){

    }

    public Paquete(String descripcion, Double precio, String titulo){
        this.setDescripcion(descripcion);
        this.setPrecio(precio);
        this.setTitulo(titulo);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
