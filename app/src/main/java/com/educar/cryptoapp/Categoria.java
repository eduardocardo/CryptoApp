package com.educar.cryptoapp;

import java.io.Serializable;

/**
 * Created by Eduardo on 10/04/2016.
 */
public class Categoria implements Serializable{

    private int idCategoria;
    private String nombre;
    private int icono;

    public Categoria(int id,String n, int i)
    {
        idCategoria = id;
        nombre = n;
        icono = i;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }


    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }
}
