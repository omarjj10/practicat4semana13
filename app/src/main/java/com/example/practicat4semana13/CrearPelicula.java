package com.example.practicat4semana13;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.practicat4semana13.entities.Pelicula;
import com.example.practicat4semana13.factories.RetrofitFactory;
import com.example.practicat4semana13.servicio.PeliculaService;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CrearPelicula extends AppCompatActivity {
    Pelicula pelicula=new Pelicula();
    static final int REQUEST_IMAGE_CAPTURE = 1000;
    static final int REQUEST_CAMERA_PERMISSION = 100;
    static final int REQUEST_PICK_IMAGE=1001;
    ImageView ivPreview;
    public static Bitmap imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pelicula);
        Button crear = findViewById(R.id.btnCrearPelicula);
        Button btnTakePhoto = findViewById(R.id.btnTakePhoto);
        ivPreview = findViewById(R.id.ivPreview);
        EditText edTitulo=findViewById(R.id.editTitulo);
        EditText edSinopsis=findViewById(R.id.editSinopsis);
        EditText edImagen=findViewById(R.id.editImagen);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //voy a solicitar si tiene permiso de camara
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                    //si no, solicito los permisos
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }
            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edTitulo.getText().toString().isEmpty() && !edSinopsis.getText().toString().isEmpty()&& !edImagen.getText().toString().isEmpty()){
                    Log.i("APP_VJ20202","si tiene texto");
                    Retrofit retrofit= RetrofitFactory.build();
                    PeliculaService service = retrofit.create(PeliculaService.class);
                    pelicula.titulo=String.valueOf(edTitulo.getText());
                    pelicula.sinopsis=String.valueOf(edSinopsis.getText());
                    pelicula.imagen=String.valueOf(edImagen.getText());
                    Call<Pelicula> call=service.create(pelicula);
                    call.enqueue(new Callback<Pelicula>() {
                        @Override
                        public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                            if(response.isSuccessful()){
                                Log.i("APP_VJ20202", new Gson().toJson(response.body()));
                            }
                        }

                        @Override
                        public void onFailure(Call<Pelicula> call, Throwable t) {
                            Log.e("APP_VJ20202","No nos podemos conectar al servicio web");
                        }
                    });
                }else{
                    Log.i("APP_VJ20202","no tiene texto");
                    mostrarDialogo();
                }
            }
        });
    }
    private void mostrarDialogo(){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Tienes que llenar todos los campos de texto")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("APP_VJ20202","Se cancelo la accion");
                    }
                })
                .show();
    }
    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e){
            //error
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK && data != null){
            //procesar la imagen
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagen=imageBitmap;
            ivPreview.setImageBitmap(imageBitmap);
        }
    }
}