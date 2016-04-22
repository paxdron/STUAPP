package com.padron.stuapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by antonio on 21/04/16.
 */
public class AvisosFragment extends Fragment {
    /*
    Etiqueta de depuracion
     */
    private static final String TAG = AvisosFragment.class.getSimpleName();

    /*
    Adaptador del recycler view
     */
    private AdaptadorAvisos adapter;

    /*
    instancia global del administrador
     */
    private Gson gson = new Gson();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private View view;


    public static AvisosFragment newInstance() {
        return new AvisosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_avisos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.reciclador);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        cargarAdaptador();
    }

    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void cargarAdaptador() {
        // Petición GET
        String requestBody=null;
        VolleySingleton.
                getInstance(getActivity()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                getString(R.string.getAvisos).toString(),
                                requestBody,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarRespuesta(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.getMessage());
                                    }
                                }

                        )
                );
    }

    /**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     * @param response Objeto Json con la respuesta
     */
    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("avisos");
                    // Parsear con Gson
                    Aviso[] avisos = gson.fromJson(mensaje.toString(), Aviso[].class);
                    // Inicializar adaptador

                    adapter = new AdaptadorAvisos(Arrays.asList(invertir(avisos)), getActivity());
                    // Setear adaptador a la lista

                    mAdapter = new RecyclerViewMaterialAdapter(adapter);
                    mRecyclerView.setAdapter(mAdapter);
                    MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Aviso []invertir(Aviso []vector) {
        int longitud = vector.length; /*creo un vector auxuliar con la misma longitud del vector a invertir*/
        Aviso[] vectorAuxiliar = new Aviso[longitud];
        for (int i = 0; i < longitud; i++) { /*la razon del - 1, es porque la posicion de un vector de n longitud (tamaño) es n - 1; ejemplo: la ultima posicion de un vector de longitud 5 es 4 */
            vectorAuxiliar[(longitud - 1) - i] = vector[i];
        }
        return vectorAuxiliar;
    }

}
