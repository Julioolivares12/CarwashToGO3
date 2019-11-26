package com.julio.carwashtogo3.model;

public class Encargado {

    private String Empresaid;
    private String encargadoId;
    private String Uid;
    private String nit;

    public String getEmpresaid() {
        return Empresaid;
    }

    public void setEmpresaid(String empresaid) {
        Empresaid = empresaid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(String encargadoId) {
        this.encargadoId = encargadoId;
    }
}
