package com.example.practicat4semana13.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.practicat4semana13.entities.Pelicula;

import java.util.List;

@Dao
public interface PeliculaDao {
    @Query("SELECT * FROM peliculas")
    List<Pelicula> getAll();
    @Query("SELECT * FROM peliculas WHERE id=:id")
    Pelicula find (int id);
    @Query("SELECT * FROM peliculas WHERE titulo=:titulo")
    Pelicula find2 (String titulo);
    @Query("SELECT * FROM peliculas WHERE sinopsis=:sinopsis")
    Pelicula find3 (String sinopsis);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Pelicula pelicula );
}
