package com.padron.stuapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by antonio on 21/04/16.
 */
public class AdaptadorAvisos extends RecyclerView.Adapter<AdaptadorAvisos.AvisoViewHolder> implements ItemClickListener{

    /**
     * Lista de objetos {@link Aviso} que representan la fuente de datos
     * de inflado
     */
    private List<Aviso> items;
    /*
    Contexto donde actua el recycler view
     */
    private Context context;

    public AdaptadorAvisos(List<Aviso> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @Override
    public AvisoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);
        return new AvisoViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(AvisoViewHolder viewHolder, int i) {
        viewHolder.fecha.setText(items.get(i).getFecha());
        viewHolder.contenido.setText(items.get(i).getContenido());
        viewHolder.icono.setImageResource(
                (items.get(i).getTipo()==0)
                        ?R.drawable.ic_notifications_24dp
                        :(items.get(i).getTipo()==1)
                            ?R.drawable.ic_schedule_24dp
                            :R.drawable.ic_map_24dp);
        ;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public static class AvisoViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView fecha;
        public TextView contenido;
        public ImageView icono;
        public ItemClickListener listener;

        public AvisoViewHolder(View v, ItemClickListener listener) {
            super(v);
            fecha = (TextView) v.findViewById(R.id.tvFecha);
            contenido = (TextView) v.findViewById(R.id.tvContenido);
            icono = (ImageView) v.findViewById(R.id.iconAviso);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}
interface ItemClickListener {
    void onItemClick(View view, int position);
}