package be.project.sitereck.Construction_Manager.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;
import be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences;
import be.project.sitereck.R;


public class particular_project extends AppCompatActivity {
    TextView title, start_date, end_date, completed_work, ongoing_work, blocked_work;
    Button btn_location;
    RequestQueue requestQueue;
    String data = "";
    String ongoing;
    private String JSON_URL="https://sitereck-1.000webhostapp.com/API/status.php";
    private String JSON_URL2="https://sitereck-1.000webhostapp.com/API/ongoingActivityStatus.php";

    be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences setSharedPprefrences = new be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences(this);
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_project);

        title = (TextView) findViewById(R.id.text_title);
        start_date = (TextView) findViewById(R.id.text_startdate);
        end_date = (TextView) findViewById(R.id.text_enddate);
       // btn_location = (Button) findViewById(R.id.btn_location);
        completed_work = (TextView) findViewById(R.id.complete_text);
        ongoing_work = (TextView) findViewById(R.id.ongoing_text);
        blocked_work = (TextView) findViewById(R.id.blocked_text);
        //btn_location.setOnClickListener((View.OnClickListener) this);

        try {
            ProjectDataClass projectDataClass = SetSharedPrefrences.getInstance(this).getprojectinfo();
            title.setText(String.valueOf(projectDataClass.getTitle()));
            start_date.setText(String.valueOf(projectDataClass.getStartDate()));
            end_date.setText(String.valueOf(projectDataClass.getEndDate()));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        JSON_Completed();
        JSON_Ongoing();
    }

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_location:
                Intent intent = new Intent(particular_project.this, profile_cm.class);
                startActivity(intent);
        }
    }*/

    public void JSON_Ongoing(){
        final String proj_id = setSharedPprefrences.getProjectId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ongoing_work.setText( jsonObject.getString( "ongoing" ) );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(particular_project.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("proj_id",proj_id);
                return params;


            }
        };


        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void JSON_Completed(){
        final String proj_id = setSharedPprefrences.getProjectId();
       StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {

           @Override
           public void onResponse(String response) {
               try {
                   JSONObject jsonObject = new JSONObject(response);
                     completed_work.setText( jsonObject.getString( "completed" ) );

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(particular_project.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<>();
               params.put("proj_id",proj_id);
               return params;
           }
       };

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
