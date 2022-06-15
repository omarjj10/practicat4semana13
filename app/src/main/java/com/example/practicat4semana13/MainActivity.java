package com.example.practicat4semana13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.practicat4semana13.Adaptador.PeliculaAdapter;
import com.example.practicat4semana13.Dao.PeliculaDao;
import com.example.practicat4semana13.database.AppDatabase;
import com.example.practicat4semana13.entities.Pelicula;
import com.example.practicat4semana13.factories.RetrofitFactory;
import com.example.practicat4semana13.servicio.PeliculaService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    List<Pelicula> peliculas =new ArrayList<>();
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=AppDatabase.getDatabase(getApplicationContext());
        FloatingActionButton fabButton=findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CrearPelicula.class);
                startActivity(intent);
            }
        });
        Retrofit retrofit= RetrofitFactory.build();
        PeliculaService service = retrofit.create(PeliculaService.class);
        Call<List<Pelicula>> call=service.GetPelicula();
        call.enqueue(new Callback<List<Pelicula>>() {
            @Override
            public void onResponse(Call<List<Pelicula>> call, Response<List<Pelicula>> response) {
                if(!response.isSuccessful()){
                    Log.e("APP_VJ20202","Error de aplicacion");
                }else{
                    Log.i("APP_VJ20202","Respuesta correcta");
                    Log.i("APP_VJ20202",new Gson().toJson(response.body()));
                    peliculas =response.body();
                    saveInDatabase(peliculas);
                    retornarInfo();
                    PeliculaAdapter adapter = new PeliculaAdapter(peliculas);
                    RecyclerView rv = findViewById(R.id.rvPeliculas);
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setHasFixedSize(true);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Pelicula>> call, Throwable t) {
                Log.e("APP_VJ20202","No hubo conectividad con el servicio web");
            }
        });
    }
    private void saveInDatabase(List<Pelicula>peliculas){
        PeliculaDao dao = db.peliculaDao();
        for(Pelicula pelicula:peliculas){
            dao.create(pelicula);
        }
    }
    private void retornarInfo(){
        PeliculaDao dao = db.peliculaDao();
        List<Pelicula>pelis=dao.getAll();
        Log.i("APP_VJ20202",new Gson().toJson(pelis));
    }
}