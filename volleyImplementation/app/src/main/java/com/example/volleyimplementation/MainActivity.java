package com.example.volleyimplementation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
    private EditText uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        results = (TextView) findViewById(R.id.result);
        profiles = (Button) findViewById(R.id.profile);
        uid = (EditText) findViewById(R.id.uid);




        requestQueue = Volley.newRequestQueue(this);
        profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userid = Integer.parseInt(uid.getText().toString());
                getProfiles(userid);
                profiles.setVisibility(View.GONE);
                uid.setVisibility(View.GONE);
            }
        });
    }

    private void getProfiles(final int usern){
        String url = "https://reqres.in/api/users?page=1";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                   //for(int i = 0 ; i <jsonArray.length(); i++){
                        JSONObject user = jsonArray.getJSONObject(usern-1);

                        int id = user.getInt("id");
                        String email = user.getString("email");
                        String first_name = user.getString("first_name");
                        String last_name = user.getString("last_name");
                        String avatar = user.getString("avatar");

                        results.append("ID: "+String.valueOf(id) + "\n" + "Email: "+email + "\n" +"First Name: "+ first_name + "\n" + "Last Name: "+last_name + "\n\n");
                        imageView=(ImageView)findViewById(R.id.imageView);
                        Picasso.get().load(avatar).into(imageView);

                  //}


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
