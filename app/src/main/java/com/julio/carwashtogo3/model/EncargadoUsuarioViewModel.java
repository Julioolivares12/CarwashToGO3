package com.julio.carwashtogo3.model;

public class EncargadoUsuarioViewModel {
    private Encargado encargado;
    private User usuario;

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Encargado getEncargado() {
        return encargado;
    }

    public void setEncargado(Encargado encargado) {
        this.encargado = encargado;
    }

    @Override
    public String toString(){
        return getUsuario ().getNombre ();
    }
}
