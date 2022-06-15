package com.example.practicat4semana13.Adaptador;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicat4semana13.PeliculaActivity;
import com.example.practicat4semana13.R;
import com.example.practicat4semana13.entities.Pelicula;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>{
    public static int codigo;
    List<Pelicula> pelis;
    public PeliculaAdapter(List<Pelicula> pelis){
        this.pelis=pelis;
    }
    @NonNull
    @Override
    public PeliculaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelicula,parent,false);
        return new PeliculaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaViewHolder vh, int position) {
        View itemView = vh.itemView;
        Pelicula peli=pelis.get(position);
        TextView tvTitulo = itemView.findViewById(R.id.tvTitulo);
        TextView tvSinopsis = itemView.findViewById(R.id.tvSinopsis);
        ImageView ivAvatar = itemView.findViewById(R.id.ivImagen);
        tvTitulo.setText(peli.titulo);
        tvSinopsis.setText(peli.sinopsis);
        Picasso.get().load("https://i.ibb.co/vqJ7XL4/batman.png").into(ivAvatar);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), PeliculaActivity.class);
                String animeJSON=new Gson().toJson(peli);
                intent.putExtra("Pelicula",animeJSON);
                codigo=peli.id;
                itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pelis.size();
    }

    class PeliculaViewHolder extends RecyclerView.ViewHolder{

        public PeliculaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
