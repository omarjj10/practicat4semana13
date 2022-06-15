package com.example.practicat4semana13.servicio;

import com.example.practicat4semana13.PeliculaActivity;
import com.example.practicat4semana13.entities.Pelicula;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PeliculaService {
    @GET("pelicula")
    Call<List<Pelicula>> GetPelicula();
    @GET("pelicula/{id}")
    Call<Pelicula> findPelicula(@Path("id") int a);
    @POST("pelicula")
    Call<Pelicula>create(@Body Pelicula pelicula);
    @PUT("pelicula/{id}")
    Call<Pelicula>update(@Path("id") int a,@Body Pelicula pelicula);
    @DELETE("pelicula/{id}")
    Call<Pelicula>delete(@Path("id") int a);
}
