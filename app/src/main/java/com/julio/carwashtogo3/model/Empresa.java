package com.julio.carwashtogo3.model;

public class Empresa {
    private String Uid;
    private String nombreEmpresa;
    private String Encargado;
    private String Ubicacion;
    private String Telefono;

    private String nivel;
    private String urlImagen;

    public Empresa() {
    }

    public Empresa(String nombreEmpresa, String encargado, String ubicacion, String telefono, String nivel, String urlImagen,String Uid) {
        this.nombreEmpresa = nombreEmpresa;
        Encargado = encargado;
        Ubicacion = ubicacion;
        Telefono = telefono;
        this.nivel = nivel;
        this.urlImagen = urlImagen;
        this.Uid= Uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getEncargado() {
        return Encargado;
    }

    public void setEncargado(String encargado) {
        Encargado = encargado;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
