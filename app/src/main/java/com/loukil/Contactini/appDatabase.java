package com.loukil.Contactini;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {prodb.class}, version = 1)
public abstract class appDatabase extends RoomDatabase {
    public abstract prodbdao PRO() ;
}
