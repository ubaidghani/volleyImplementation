package com.example.volleyimplementation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView results;
    private Button profiles;
    private RequestQueue requestQueue;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        results = (TextView) findViewById(R.id.result);
        profiles = (Button) findViewById(R.id.profile);




        requestQueue = Volley.newRequestQueue(this);
        profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProfiles();
                profiles.setVisibility(View.GONE);
            }
        });
    }

    private void getProfiles(){
        String url = "https://reqres.in/api/users?page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i = 0 ; i <jsonArray.length(); i++){
                        JSONObject user = jsonArray.getJSONObject(i);

                        int id = user.getInt("id");
                        String email = user.getString("email");
                        String first_name = user.getString("first_name");
                        String last_name = user.getString("last_name");
                        String avatar = user.getString("avatar");

                        results.append("ID: "+String.valueOf(id) + "\n" + "Email: "+email + "\n" +"First Name: "+ first_name + "\n" + "Last Name: "+last_name + "\n" + "Profile: "+ avatar +"\n\n");

                   }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
