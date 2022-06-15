package com.example.practicat4semana13.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.practicat4semana13.Dao.PeliculaDao;
import com.example.practicat4semana13.entities.Pelicula;

@Database(entities = {Pelicula.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PeliculaDao peliculaDao();
    public static AppDatabase getDatabase(Context context){
        return Room.databaseBuilder(context,AppDatabase.class, "com.example.practicat4semana13.database.peliculas_db")
                .allowMainThreadQueries()
                .build();
    }
}
