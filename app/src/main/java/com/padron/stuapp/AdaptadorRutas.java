package com.padron.stuapp;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adaptador para comidas usadas en la sección "Categorías"
 */
public class AdaptadorRutas
        extends RecyclerView.Adapter<AdaptadorRutas.ViewHolder> {


    private final List<Ruta> items;

    public AdaptadorRutas(List<Ruta> items, OnItemClickListener escuchaClicksExterna) {
        this.items = items;
        this.escuchaClicksExterna = escuchaClicksExterna;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_rutas, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Ruta item = items.get(i);

        /*Glide.with()
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);*/
        Picasso.with(viewHolder.itemView.getContext()).load(item.getImagen()).into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Campos respectivos de un item
        public TextView nombre;
        public ImageView imagen;

        public ViewHolder(View v) {
            super(v);
            v.setClickable(true);
            nombre = (TextView) v.findViewById(R.id.nombre_ruta);
            imagen=(ImageView)v.findViewById(R.id.miniatura_ruta);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escuchaClicksExterna.onClick(this, obtenerNombreRuta(getAdapterPosition()-2));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private OnItemClickListener escuchaClicksExterna;


    private String obtenerNombreRuta(int posicion) {
        if (posicion != RecyclerView.NO_POSITION) {
            return items.get(posicion).getNombre();
        } else {
            return null;
        }
    }

    public interface OnItemClickListener {
        public void onClick(ViewHolder viewHolder, String nombreRuta);
    }
}
