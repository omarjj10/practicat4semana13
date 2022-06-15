package com.example.practicat4semana13.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "peliculas")
public class Pelicula {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "titulo")
    public String titulo;
    @ColumnInfo(name = "sinopsis")
    public String sinopsis;
    public String imagen;

    public Pelicula() {
    }

    public Pelicula(int id) {
        this.id = id;
    }

    public Pelicula(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Pelicula(int id, String titulo, String sinopsis) {
        this.id = id;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
    }

    public Pelicula(int id, String titulo, String sinopsis, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
    }
}
