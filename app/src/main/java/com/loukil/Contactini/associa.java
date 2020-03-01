package com.loukil.Contactini;

public class associa {
    private String nom ;
    private String adres ;
    private String category ;
    private String tel ;
    private float rating ;
    private String logopic ;
    private String id;
    private String bio ;

    public associa() {
    }

    public associa(String nom, String adres, String category, String tel, float rating, String logopic, String id, String bio) {
        this.nom = nom;
        this.adres = adres;
        this.category = category;
        this.tel = tel;
        this.rating = rating;
        this.logopic = logopic;
        this.id = id;
        this.bio = bio;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getLogopic() {
        return logopic;
    }

    public void setLogopic(String logopic) {
        this.logopic = logopic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
