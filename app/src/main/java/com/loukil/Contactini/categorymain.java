package com.loukil.Contactini;

public class categorymain {
    private String categoryname ;
    private Boolean hassub ;
    private int catimg ;

    public categorymain() {
    }

    public categorymain(String categoryname, Boolean hassub, int catimg) {
        this.categoryname = categoryname;
        this.hassub = hassub;
        this.catimg = catimg;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public Boolean getHassub() {
        return hassub;
    }

    public void setHassub(Boolean hassub) {
        this.hassub = hassub;
    }

    public int getCatimg() {
        return catimg;
    }

    public void setCatimg(int catimg) {
        this.catimg = catimg;
    }
}
