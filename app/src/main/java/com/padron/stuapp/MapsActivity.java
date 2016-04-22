package com.padron.stuapp;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.kml.KmlLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private Gson gson = new Gson();
    private unidadEnServicio[] current;
    private TextView titulo;
    private String nombreRuta;
    private int mapaIda,mapaRegreso,rutas,currentMap;
    private ArrayAdapter<CharSequence> adapter;
    private Button selectRecorrido;
    private int rutaSelect, indiceRuta;
    private Handler mHandler;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e("Handlers", "Calls");
            cargarAdaptador();
            mHandler.postDelayed(mRunnable, 5000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        titulo=(TextView)findViewById(R.id.tvNombreRuta);
        RutaSelect();
        titulo.setText(nombreRuta);
        currentMap=mapaRegreso;
        indiceRuta=rutaSelect*2;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        selectRecorrido= (Button)findViewById(R.id.btnSelectRuta);
        adapter= ArrayAdapter.createFromResource(this,
                rutas,R.layout.support_simple_spinner_dropdown_item);
        selectRecorrido.setText(adapter.getItem(0));
        selectRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMap == mapaIda) {
                    indiceRuta=rutaSelect*2;
                    currentMap = mapaRegreso;
                    selectRecorrido.setText(adapter.getItem(0));
                } else {
                    currentMap = mapaIda;
                    indiceRuta=rutaSelect*2+1;
                    selectRecorrido.setText(adapter.getItem(1));
                }
                updateMapa();
                cargarAdaptador();
            }
        });
        //cargarAdaptador();
        mHandler=new Handler();
        mHandler.post(mRunnable);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Puebla = new LatLng(19.041428, -98.206300);
        mMap.addMarker(new MarkerOptions().position(Puebla).title("Puebla"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Puebla, 12));
        updateMapa();

    }

    public void updateMapa(){
        try {
            mMap.clear();
            KmlLayer layer = new KmlLayer(mMap, currentMap, getApplicationContext());
            layer.addLayerToMap();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showToast(String message, Context context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void cargarAdaptador() {
        // Petición GET
        String requestBody=null;
        VolleySingleton.
                getInstance(getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                getString(R.string.getCurrentVehicles),
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
                    JSONArray mensaje = response.getJSONArray("Current");
                    // Parsear con Gson
                     current = gson.fromJson(mensaje.toString(), unidadEnServicio[].class);
                    addMarkers();
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void addMarkers(){
        updateMapa();
        for (int i=0;i<current.length;i++) {
            if(current[i].idRuta==indiceRuta) {
                LatLng newPoint = new LatLng(current[i].latitud, current[i].longitud);
                mMap.addMarker(new MarkerOptions().position(newPoint).title(Integer.toString(i)).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
            }
        }
        //mMap.
    }

    public void RutaSelect(){
        nombreRuta= getIntent().getStringExtra(Principal.RUTA_SELECT);
        switch(nombreRuta){
            case "Amalucan":
                mapaIda=R.raw.cu_amalucan;
                mapaRegreso=R.raw.amalucan_cu;
                rutas=R.array.amalucan;
                rutaSelect=0;
                break;
            case "CAPU":
                mapaIda=R.raw.cu_capu;
                mapaRegreso=R.raw.capu_cu;
                rutas=R.array.capu;
                rutaSelect=1;
                break;
            case "Cuautlancingo":
                mapaIda=R.raw.cu_cuautlancingo;
                mapaRegreso=R.raw.cuautlancingo_cu;
                rutas=R.array.cuautlancingo;
                rutaSelect=2;
                break;
            case "Los Héroes":
                mapaIda=R.raw.cu_heroes;
                mapaRegreso=R.raw.heroes_cu;
                rutas=R.array.heroes;
                rutaSelect=3;
                break;
            case "Maravillas":
                mapaIda=R.raw.cu_maravillas;
                mapaRegreso=R.raw.maravillas_cu;
                rutas=R.array.maravillas;
                rutaSelect=4;
                break;
            case "San Ramón":
                mapaIda=R.raw.cu_sanramon;
                mapaRegreso=R.raw.sanramon_cu;
                rutas=R.array.sanramon;
                rutaSelect=5;
                break;
        }
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mRunnable);
        super.onPause();
    }

    @Override
    public void onResume() {
        mHandler.post(mRunnable);
        super.onResume();
    }

}
