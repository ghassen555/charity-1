package com.loukil.Contactini;

public class category {
    private String nomcat ;
    private int catimg ;

    public category() {
    }

    public category(String nomcat, int catimg) {
        this.nomcat = nomcat;
        this.catimg = catimg;
    }

    public String getNomcat() {
        return nomcat;
    }

    public void setNomcat(String nomcat) {
        this.nomcat = nomcat;
    }

    public int getCatimg() {
        return catimg;
    }

    public void setCatimg(int catimg) {
        this.catimg = catimg;
    }
}

