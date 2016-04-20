package com.padron.stuapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.padron.stuapp.R;
import com.padron.stuapp.TestRecyclerViewAdapter;
import com.padron.stuapp.fragment.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RutasFragment extends Fragment implements AdaptadorRutas.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private View view;

    private static final int COLS = 2;
    private EscuchaFragmento escucha;

    public static RutasFragment newInstance() {
        return new RutasFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rutas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvRutas);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),COLS);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new AdaptadorRutas(Ruta.RUTAS_BUAP,this),COLS);
        mRecyclerView.setAdapter(mAdapter);


        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    @Override
    public void onClick(AdaptadorRutas.ViewHolder viewHolder, String nombreRuta) {
        Toast.makeText(view.getContext(), "WEA: "+nombreRuta, Toast.LENGTH_SHORT).show();
        escucha.alSeleccionarItem(nombreRuta);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EscuchaFragmento) {
            escucha = (EscuchaFragmento) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " debes implementar EscuchaFragmento");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        escucha = null;
    }

    public interface EscuchaFragmento {
        void alSeleccionarItem(String idArticulo);
    }


}