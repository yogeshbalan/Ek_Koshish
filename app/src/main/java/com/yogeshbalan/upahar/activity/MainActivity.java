package com.yogeshbalan.upahar.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yogeshbalan.upahar.Constants;
import com.yogeshbalan.upahar.R;
import com.yogeshbalan.upahar.adapter.NGORecyclerViewAdapter;
import com.yogeshbalan.upahar.model.Geopoint;
import com.yogeshbalan.upahar.model.NGO;
import com.yogeshbalan.upahar.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private final String TAG = "test";
    private ProgressDialog progressDialog;
    private String jsondata;
    private List<NGO> ngoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NGORecyclerViewAdapter ngoRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */

        /*sessionManager.checkLogin();

        User user;
        user = sessionManager.getUserDetails();

        // name
        String name = user.getUsername();

        // email
        String email = user.getEmail();
        Log.v("login",  "username and email = " + name + email);*/


        recyclerView = (RecyclerView) findViewById(R.id.ngo_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NgoMapsActivity.class));
            }
        });
        //FetchNGO_List();
        try {
            ngoList = parseJson(getJsondata());
            ngoRecyclerViewAdapter = new NGORecyclerViewAdapter(ngoList, MainActivity.this);
            recyclerView.setAdapter(ngoRecyclerViewAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void FetchNGO_List() {
        Log.v(TAG, "fetchData fo Courses is called");
        volleySingleton = VolleySingleton.getMyInstance();
        requestQueue = volleySingleton.getRequestQueue();
        showProgressDialog();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.1.5:5000/ngo_info/Maharashtra", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsondata=response.toString();
                try {
                    ngoList = parseJson(response.toString());
                    ngoRecyclerViewAdapter = new NGORecyclerViewAdapter(ngoList, MainActivity.this);
                    recyclerView.setAdapter(ngoRecyclerViewAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (response == null) {
                    Log.v(TAG, "fetchData is not giving a fuck");
                }
                Log.v(TAG, "response = " + response);
                progressDialog.dismiss();
            }
            }

    , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.v(TAG, "Response = " + "timeOut");
                } else if (error instanceof AuthFailureError) {
                    Log.v(TAG, "Response = " + "AuthFail");
                } else if (error instanceof ServerError) {
                    Log.v(TAG, "Response = " + "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.v(TAG, "Response = " + "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.v(TAG, "Response = " + "ParseError");
                }
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public String getJsondata() {
        // Reading json file from assets folder
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(
                    "test.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jsondata = sb.toString();
        return jsondata;
    }

    public List<NGO> parseJson(String s) throws JSONException {
        List<NGO> list = new ArrayList<>();
        List<String> imageUrlList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(s);
        JSONArray jsonArray = jsonObject.getJSONArray(Constants.NGO_ALL);

        for (int i = 0; i < jsonArray.length(); i++) {
            NGO ngo = new NGO();
            JSONObject object = jsonArray.getJSONObject(i);
            ngo.setAddress(object.getString(Constants.ADDRESS));
            ngo.setDescription(object.getString(Constants.DESCRIPTION));
            ngo.setEmail(object.getString(Constants.EMAIL));

            Geopoint geopoint = new Geopoint();
            JSONObject geoObject = object.getJSONObject(Constants.GEOPOINT);
            geopoint.setLat(Double.valueOf(geoObject.getString(Constants.LATTITUDE)));
            geopoint.setLng(Double.valueOf(geoObject.getString(Constants.LONGITUDE)));
            ngo.setGeopoint(geopoint);

            JSONArray imageResponseArray = object.getJSONArray(Constants.IMAGE_URL);
            for (int k = 0; k < imageResponseArray.length(); k++) {
                String url;
                url = imageResponseArray.getString(k);
                imageUrlList.add(url);
                Log.v(TAG, String.valueOf(imageUrlList));
            }

            ngo.setImageUrls(imageUrlList);
            ngo.setLogoUrl(object.getString(Constants.LOGO_URL));
            ngo.setName(object.getString(Constants.NAME));
            ngo.setObjectId(object.getString(Constants.OBJECT_ID));
            ngo.setPhoneNo(object.getString(Constants.PHONE));
            ngo.setSlogan(object.getString(Constants.SLOGAN));
            ngo.setWebsiteUrl(object.getString(Constants.WEBSITE_URL));

            list.add(ngo);

        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.event_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting NGO list");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

}
