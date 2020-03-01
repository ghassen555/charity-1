package com.loukil.Contactini;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "Favourite")
public class prodb {


    public prodb(@NonNull String favid, String nom, String ville, String tel, String category, String link, float avgrating) {
        this.favid = favid;
        this.nom = nom;
        this.ville = ville;
        this.tel = tel;
        this.category = category;
        this.link = link;
        this.avgrating = avgrating;
    }

    @NonNull
    public String getFavid() {
        return favid;
    }

    public void setFavid(@NonNull String favid) {
        this.favid = favid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public float getAvgrating() {
        return avgrating;
    }

    public void setAvgrating(float avgrating) {
        this.avgrating = avgrating;
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String favid;

    @ColumnInfo(name = "nom")
    public String nom;

    @ColumnInfo(name = "ville")
    public String ville;

    @ColumnInfo(name = "tel")
    public String tel;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "link")
    public String link;

    @ColumnInfo(name = "avgrating")
    private float avgrating;

}
