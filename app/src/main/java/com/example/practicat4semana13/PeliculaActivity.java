package com.example.practicat4semana13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practicat4semana13.Adaptador.PeliculaAdapter;
import com.example.practicat4semana13.entities.Pelicula;
import com.example.practicat4semana13.factories.RetrofitFactory;
import com.example.practicat4semana13.servicio.PeliculaService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PeliculaActivity extends AppCompatActivity {
    List<Pelicula> pelicula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula);
        Button btnEditar=findViewById(R.id.btnEditar);
        Button btnEliminar=findViewById(R.id.btnEliminar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),EditarPelicula.class);
                startActivity(intent);
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit= RetrofitFactory.build();
                PeliculaService service=retrofit.create(PeliculaService.class);
                Call<Pelicula> call=service.delete(PeliculaAdapter.codigo);
                call.enqueue(new Callback<Pelicula>() {
                    @Override
                    public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                        if(response.isSuccessful()){
                            Log.i("APP_VJ20202", "Se elimino correctamente al contacto " + PeliculaAdapter.codigo);
                            pelicula.remove(PeliculaAdapter.codigo);
                        }
                        else{
                            Log.i("APP_VJ20202","No se pudo eliminar el contacto");
                        }
                    }

                    @Override
                    public void onFailure(Call<Pelicula> call, Throwable t) {
                        Log.i("APP_VJ20202","No nos podemos conectar al servicio web");
                    }
                });
            }
        });
        String peliculaJSON=getIntent().getStringExtra("Pelicula");
        Pelicula pelicula= new Gson().fromJson(peliculaJSON,Pelicula.class);
        ImageView ivAvatar =findViewById(R.id.ivImagen);
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        TextView tvSinopsis=findViewById(R.id.tvSinopsis);
        Picasso.get().load("https://i.ibb.co/vqJ7XL4/batman.png").into(ivAvatar);
        tvTitulo.setText(pelicula.titulo);
        tvSinopsis.setText(pelicula.sinopsis);
    }
}