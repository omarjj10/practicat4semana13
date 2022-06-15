package com.example.practicat4semana13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.practicat4semana13.Adaptador.PeliculaAdapter;
import com.example.practicat4semana13.entities.Pelicula;
import com.example.practicat4semana13.factories.RetrofitFactory;
import com.example.practicat4semana13.servicio.PeliculaService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditarPelicula extends AppCompatActivity {
    Pelicula pelicula = new Pelicula();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pelicula);
        Button btnEditar = findViewById(R.id.btnEditarPelicula);
        EditText edTitulo = findViewById(R.id.editNuevoTitulo);
        EditText edSinopsis = findViewById(R.id.editNuevaSinposis);
        EditText edImagen = findViewById(R.id.editNuevaImagen);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit= RetrofitFactory.build();
                PeliculaService service=retrofit.create(PeliculaService.class);
                pelicula.titulo=String.valueOf(edTitulo.getText());
                pelicula.sinopsis=String.valueOf(edSinopsis.getText());
                pelicula.imagen=String.valueOf(edImagen.getText());
                Call<Pelicula> call=service.update(PeliculaAdapter.codigo,pelicula);
                call.enqueue(new Callback<Pelicula>() {
                    @Override
                    public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                        Log.i("APP_VJ20202", new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<Pelicula> call, Throwable t) {
                        Log.i("APP_VJ20202","No nos podemos conectar al servicio web");
                    }
                });
            }
        });
    }
}