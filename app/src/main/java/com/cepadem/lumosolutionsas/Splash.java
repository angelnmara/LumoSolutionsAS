package com.cepadem.lumosolutionsas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private final String splash = "Splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                validaToken();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void validaToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        if(token!=""){
            esTokenValido(token);
        }else{
            openLogin();
        }
    }

    private void openMain(){
        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Splash.this.startActivity(mainIntent);
        Splash.this.finish();
    }

    private void openLogin(){
        Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Splash.this.startActivity(mainIntent);
        Splash.this.finish();
    }

    private void esTokenValido(String token) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String server = getResources().getString(R.string.server);
        String endPointLogin = getResources().getString(R.string.validaToken);
        String url = server + endPointLogin + token;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //showProgress(false);
                    JSONObject jsonObjectMsg = response.getJSONObject(getString(R.string.message));
                    if(jsonObjectMsg.getBoolean(getString(R.string.isSuccessStatusCode))){
                        Log.d(splash, response.toString());
                        if(response.getBoolean(getString(R.string.valido))){
                            openMain();
                        }else{
                            openLogin();
                        }
                    }else{
                        openLogin();
                        Log.d(splash, getString(R.string.peticionIncorrecta));
                    }
                } catch (JSONException e) {
                    openLogin();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //showProgress(false);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}
