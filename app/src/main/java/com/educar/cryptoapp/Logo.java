package com.educar.cryptoapp;

/**
 * Created by Eduardo on 06/05/2016.
 */
public class Logo  {

    private int id;
    //indica el nombre del logro
    private String nombre;
    //indica la imagen que tiene este logo
    private int imagen;
    private int imagenGrande;
    private int color;

    public Logo(int i,String n,int im,int imG,int c)
    {
        id = i;
        nombre = n;
        imagen = im;
        imagenGrande = imG;
        color = c;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getImagenGrande() {
        return imagenGrande;
    }

    public void setImagenGrande(int imagenGrande) {
        this.imagenGrande = imagenGrande;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
