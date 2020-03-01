package com.loukil.Contactini;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface prodbdao {
   @Query("SELECT * FROM favourite")
   List<prodb> getAllUsers();

    @Query("SELECT EXISTS (SELECT 1 FROM Favourite WHERE id=:proid)")
    int isFavourite(String proid);

    @Insert
    void insertAll(prodb PRO);

    @Delete
    void delete(prodb PRO);


}
