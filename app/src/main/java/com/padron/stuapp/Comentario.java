package com.padron.stuapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class Comentario extends Activity {

    private static final String TAG = Comentario.class.getSimpleName();
    int indiceRuta;
    EditText comentario;
    FancyButton ok,cancel;
    String[] rutas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);
        rutas=getResources().getStringArray(R.array.rutas);
        ok=(FancyButton)findViewById(R.id.btnOk);
        cancel=(FancyButton)findViewById(R.id.btnCancell);
        comentario=(EditText)findViewById(R.id.comentario);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Comentario;
                Comentario=comentario.getText().toString();
                if(!Comentario.equals("")) {
                    postNewComment(getApplicationContext(), new comment(1, indiceRuta, getToday(), Comentario));
                    Toast.makeText(Comentario.this, "Comentario Enviado", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(Comentario.this, "No se pueden enviar comentarios vacios", Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        indiceRuta=getIntent().getIntExtra(MapsActivity.ID_RUTA,14);
        ((TextView)findViewById(R.id.tvRuta)).setText(rutas[indiceRuta]);
    }

    public static void postNewComment(final Context context, final comment comentario){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,context.getString(R.string.newComment), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(context.getString(R.string.idUser),Integer.toString(comentario.idUser));
                params.put(context.getString(R.string.idRuta),Integer.toString(comentario.idRuta));
                params.put(context.getString(R.string.Fecha),comentario.Fecha);
                params.put(context.getString(R.string.Contenido),comentario.Contenido);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }


    public class comment{
        int idUser;
        int idRuta;
        String Fecha;
        String Contenido;

        public comment(int idUser, int idRuta, String fecha, String contenido) {
            this.idUser = idUser;
            this.idRuta = idRuta;
            Fecha = fecha;
            Contenido = contenido;
        }
    }

    public String getToday(){
        Calendar cal= Calendar.getInstance();
        return Integer.toString(cal.get(Calendar.YEAR))+"-"+Integer.toString(cal.get(Calendar.MONTH)+1)+"-"+Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
    }
}
