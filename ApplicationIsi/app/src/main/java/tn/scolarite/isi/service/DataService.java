package tn.scolarite.isi.service;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import tn.scolarite.isi.MySingleton;
import tn.scolarite.isi.activity.Login;
import tn.scolarite.isi.activity.MainActivity;
import tn.scolarite.isi.model.DemandeVerification;
import tn.scolarite.isi.model.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataService {

    public static final String URL = "http://digitalisi.tn:8080/engine-rest";
    public Context context;
    public static SharedPreferences sharedPreferences;

    public DataService(Context context) {
        this.context = context;
    }

    public interface GetTasksListener{
        void onError (String message);
        void onResponse(ArrayList<HashMap<String, String>> list);
    }

    public void getTasks( GetTasksListener getTasksListener )
    {
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        String url = URL + "/task";

        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url,
                null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse( JSONArray response )
            {
                try
                {
                    for( int i = 0; i < response.length(); i++ )
                    {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject object = response.getJSONObject(i);
                        Task task = new Task();
                        task.setId(object.getString("id"));
                        task.setName(object.getString("name"));
                        task.setDate(object.getString("created"));
                        task.setTaskDefinitionKey(object.getString("taskDefinitionKey"));
                        map.put("id", task.getId());
                        map.put("date", task.getDate().substring(0, 10));
                        map.put("name", task.getName());
                        map.put("taskDefinitionKey", task.getTaskDefinitionKey());
                        listItem.add(map);
                    }
                    getTasksListener.onResponse(listItem);
                }
                catch( JSONException e )
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Login.class);
                intent.putExtra("err", "Connexion impossible");
                context.startActivity(intent);
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() {
                JSONObject jsonBody = null;
                try {
                    jsonBody = new JSONObject("{\n" +
                            "  \"processVariables\": [],\n" +
                            "  \"taskVariables\": [],\n" +
                            "  \"caseInstanceVariables\": [],\n" +
                            "  \"firstResult\": 0,\n" +
                            "  \"maxResults\": 15,\n" +
                            "  \"sorting\": [\n" +
                            "    {\n" +
                            "      \"sortBy\": \"created\",\n" +
                            "      \"sortOrder\": \"desc\"\n" +
                            "    }\n" +
                            "  ],\n" +
                            "  \"active\": true\n" +
                            "}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String requestBody = jsonBody.toString();
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                String credentials = sharedPreferences.read(context.getApplicationContext());
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        MySingleton.getInstance( context ).addToRequestQueue( request );
    }

    public interface GetDemandeVerificationFormDataListener{
        void onError (String message);
        void onResponse(DemandeVerification demandeVerification);
    }

    public void getDemandeVerificationFormData(GetDemandeVerificationFormDataListener getDemandeVerificationFormDataListener, String id) {
        DemandeVerification demande = new DemandeVerification();
        String url = URL + "/task/"+id+"/form-variables";

        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url,
                null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse( JSONObject response )
            {
                try {
                    demande.setNom(response.getJSONObject("nom").getString("value"));
                    demande.setPrenom(response.getJSONObject("prenom").getString("value"));
                    demande.setMotif(response.getJSONObject("motif").getString("value"));
                    demande.setNiveau(response.getJSONObject("niveau").getString("value"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getDemandeVerificationFormDataListener.onResponse(demande);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    Toast.makeText(context, body, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {

                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                String credentials = sharedPreferences.read(context);
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        MySingleton.getInstance( context ).addToRequestQueue( request );

    }

    public interface SubmitListener {
        void onError (String message);
        void onResponse();
    }

    public void postForm(SubmitListener submitListener, String requestBody, String id) {
        String url = URL + "/task/"+id+"/submit-form";
        JsonObjectRequest request = new JsonObjectRequest( Request.Method.POST, url,
                null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse( JSONObject response ) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //submitListener.onError(error.toString());
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                String credentials = sharedPreferences.read(context);
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        MySingleton.getInstance( context ).addToRequestQueue( request );



    }


}
