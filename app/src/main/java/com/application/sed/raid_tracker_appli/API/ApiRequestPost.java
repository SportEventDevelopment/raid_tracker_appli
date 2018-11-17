package com.application.sed.raid_tracker_appli.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.application.sed.raid_tracker_appli.Accueil;
import com.application.sed.raid_tracker_appli.Utils.Bdd;
import com.application.sed.raid_tracker_appli.Utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.ChoiceFormat;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestPost {

    final static String urlUser = "http://raidtracker.ddns.net/raid_tracker_api/web/app.php/api/users";
    final static String urlAuthToken = "http://raidtracker.ddns.net/raid_tracker_api/web/app.php/api/auth-tokens";
    final static String urlRaid = "http://raidtracker.ddns.net/raid_tracker_api/web/app.php/api/raids";
    final static String urlParcours="http://raidtracker.ddns.net/raid_tracker_api/web/app.php/api/parcours";
    final static String urlPoints="http://raidtracker.ddns.net/raid_tracker_api/web/app.php/api/points";
    final static String urlTraces="http://raidtracker.ddns.net/raid_tracker_api/web/app.php/api/traces";


    public static void postUser(Context context, final String name, final String mail, final String pwd){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlUser,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response post us", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String>  params = new HashMap<String, String>();
//                JSONObject pass = new JSONObject();
                //Map<String, String>  params2 = new HashMap<String, String>();
                params.put("username",name);
                params.put("email",mail);
                params.put("plainPassword",pwd);

                return params;
            }
        };
        requestQueue.add(postRequest);

    }


    /**
     * FOnctionne : connexion utilisateur
     * @param context
     * @param name
     * @param pwd
     */
    public static void postToken(Context context, final String name, final String pwd){


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlAuthToken,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        Log.d("Response", response);

                        Accueil.redirection(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String>  params = new HashMap<String, String>();
                params.put("login",name);
                params.put("password",pwd);


                return params;
            }
        };
        requestQueue.add(postRequest);

    }

    public static void postRaid(Context context, final String token, final String name, final String lieu, final String date, final String edition, final String equipe){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlRaid,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response


                        Log.d("Response", response);

                        Accueil.redirection(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                //super.getHeaders();

                Map<String, String> header = new HashMap<>();
                String auth;
                Utils.debug("Header",token);
                header.put("Content-Type", "application/json");
                header.put("X-Auth-Token",token);
                return header;
            }

            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String>  params = new HashMap<>();
                params.put("nom",name);
                params.put("lieu",lieu);
                params.put("date",date);
                params.put("edition",edition);
                params.put("equipe", equipe);


                return params;
            }
        };
        requestQueue.add(postRequest);

    }

    public static void postparcours(final Context context, final String token, final String idRaid, final String idParcoursPere, final String name, final String type, final String etat){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlParcours,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        //Accueil.redirection(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                //super.getHeaders();

                Map<String, String> header = new HashMap<>();
                String auth;
                Utils.debug("Header",token);
                header.put("Content-Type", "application/json");
                header.put("X-Auth-Token",token);
                return header;
            }

            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String>  params = new HashMap<>();
                params.put("idRaid",idRaid);
                if(!idParcoursPere.equals("-1")){
                    params.put("idParcoursPere",idParcoursPere);
                }
                params.put("nom",name);
                params.put("type",type);
                params.put("etat", etat);

                return params;
            }
        };
        requestQueue.add(postRequest);

    }


    public static void postpoint(final Context context, final String token, final String idTrace, final String longitude, final String latitude, final String type){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlPoints,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        //Accueil.redirection(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                //super.getHeaders();

                Map<String, String> header = new HashMap<>();
                String auth;
                Utils.debug("Header",token);
                header.put("Content-Type", "application/json");
                header.put("X-Auth-Token",token);
                return header;
            }

            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String>  params = new HashMap<>();
                params.put("idTrace",idTrace);
                if(!longitude.equals("-1")){
                    params.put("lon",longitude);
                }
                if(!latitude.equals("-1")){
                    params.put("lat",latitude);
                }
                if(!type.equals("-1")){
                    params.put("lon",type);
                }

                return params;
            }
        };
        requestQueue.add(postRequest);

    }


    public static void postrace(final Context context, final String token, final String idParcours, final String isCalibre){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlTraces,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        //Accueil.redirection(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                //super.getHeaders();

                Map<String, String> header = new HashMap<>();
                String auth;
                Utils.debug("Header",token);
                header.put("Content-Type", "application/json");
                header.put("X-Auth-Token",token);
                return header;
            }

            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String>  params = new HashMap<>();
                params.put("idParcours",idParcours);
                if(!isCalibre.equals("-1")){
                    params.put("isCalibre",isCalibre);
                }
                return params;
            }
        };
        requestQueue.add(postRequest);

    }
}
