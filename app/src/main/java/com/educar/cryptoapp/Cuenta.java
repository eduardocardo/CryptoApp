package com.educar.cryptoapp;

import java.io.Serializable;

/**
 * Created by Eduardo on 02/05/2016.
 */
public class Cuenta implements Serializable {

    private int idCuenta;
    private String registro;
    private String usuario;
    private String pass;
    private int categoria;
    private int icono;

    public Cuenta(int id,String r,String u,String p,int c,int i)
    {
        idCuenta = id;
        registro = r;
        usuario = u;
        pass = p;
        categoria = c;
        icono = i;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
