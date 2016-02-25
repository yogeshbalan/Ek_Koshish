package com.yogeshbalan.upahar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.yogeshbalan.upahar.R;
import com.yogeshbalan.upahar.adapter.PointEarnedAdapter;
import com.yogeshbalan.upahar.model.UserLB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    TextView favcyPoint, email, username;
    RecyclerView recyclerView;
    String jsondata;
    List<UserLB> userLBList = new ArrayList<>();
    PointEarnedAdapter pointEarnedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        favcyPoint = (TextView) findViewById(R.id.favcy_point);
        email = (TextView) findViewById(R.id.profile_email);
        username = (TextView) findViewById(R.id.profile_name);

        ParseUser parseUser = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("user");
        query.whereEqualTo("username", parseUser.getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                if (e == null) {
                    if (userList.size() > 0) {

                        for (int i = 0; i < userList.size(); i++) {
                            ParseObject p = userList.get(i);
                            favcyPoint.setText("Your Favcy Points are : " + String.valueOf(p.getNumber("fhack_points")));
                            Log.v("test", "points = " + p.getNumber("fhack_points"));
                        }
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        email.setText(parseUser.getEmail());
        username.setText(parseUser.getUsername());

        recyclerView = (RecyclerView) findViewById(R.id.pointEarned_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        try {
            userLBList = parseJson(getJsondata());
            pointEarnedAdapter = new PointEarnedAdapter(userLBList, this);
            recyclerView.setAdapter(pointEarnedAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.event_leaderboard) {
            startActivity(new Intent(this, LeaderBoard.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getJsondata() {
        // Reading json file from assets folder
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(
                    "points.json")));
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

    public List<UserLB> parseJson(String s) throws JSONException {
        List<UserLB> list = new ArrayList<>();
        List<String> imageUrlList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(s);
        JSONArray jsonArray = jsonObject.getJSONArray("list");

        for (int i = 0; i < jsonArray.length(); i++) {
            UserLB userLB = new UserLB();
            JSONObject object = jsonArray.getJSONObject(i);
            userLB.setLogo_url(object.getString("logo_url"));
            userLB.setNgo(object.getString("ngo"));


            list.add(userLB);

        }
        return list;
    }


}
