package com.jci.jssai3.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getName();
    private Button btnRequest;
    private Button btnPost;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://www.mocky.io/v2/5ccd172c2e0000050f1829b6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRequest = (Button) findViewById(R.id.buttonRequest);
        btnPost = (Button) findViewById(R.id.buttonPost);
        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){

                                              sendAndRequestResponse();

                                          }
                                      }

        );
        btnPost.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v) {

               postData("1234");
           }
        });
    }
    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }
    public void postData(final String deviceid) {

        final RequestQueue queue = Volley.newRequestQueue(this);
        //this is the url where you want to send the request
        //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
        final String url = "http://10.123.45.1/api/1/netapp/set_urn";

        //  Log.d(TAG,"device id is:" + deviceID.getText());
        //final String deviceid = "5555";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Toast.makeText(getApplicationContext(),"Configured Successfully", Toast.LENGTH_LONG).show();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("errorpost", error.getMessage());

            }
        }) {


            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map headers = new HashMap<>();
                String credentials = "admin:admin1234@";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                String auth1 = "Basic YWRtaW46YWRtaW4=";
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", auth);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("__SL_P_UDI", deviceid);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
}
