package be.project.sitereck.Construction_Manager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.Construction_Manager.Adapters.ProjectListAdapter;
import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;
import be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.R;
import de.hdodenhof.circleimageview.CircleImageView;

//import be.project.sitereck.MainActivity;

public class ProjectList_cm extends AppCompatActivity implements ItemClickListener,View.OnClickListener{
    private RecyclerView recyclerView;
    ProjectListAdapter adapter;
    List<ProjectDataClass> listItems=new ArrayList<>();
    CircleImageView circleImageView;
    RequestQueue requestQueue;
    String HTTP_JSON_URL = "https://sitereck-1.000webhostapp.com/API/getProjectList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list_cm);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        circleImageView=(CircleImageView)findViewById(R.id.img);
        JSON_DATA_WEB_CALL();

        adapter = new ProjectListAdapter(listItems,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        circleImageView.setOnClickListener((View.OnClickListener)this);
    }

    private void JSON_DATA_WEB_CALL() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTP_JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("projects");

                        for(int i =0 ;i<array.length() ; i++){

                            JSONObject object = array.getJSONObject(i);
                            ProjectDataClass pd = new ProjectDataClass(object.getString("proj_name"),object.getString("proj_start_date"),object.getString("proj_end_date"),object.getString("proj_id"));
                            listItems.add(pd);
                        }
                        recyclerView.setAdapter(adapter);
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
  @Override
    public void onClick(View v, int adapterPosition) {

                listItems.get(adapterPosition);
                createSharedPreference(adapterPosition);
                Intent intent = new Intent(ProjectList_cm.this, dashboard_cm.class);
                startActivity(intent);
    }

    private void createSharedPreference(int adapterPosition) {
        new SetSharedPrefrences(this).setTitle(String.valueOf(listItems.get(adapterPosition).getTitle()));
        new SetSharedPrefrences(this).setStartDate(String.valueOf(listItems.get(adapterPosition).getStartDate()));
        new SetSharedPrefrences(this).setEndDate(String.valueOf(listItems.get(adapterPosition).getEndDate()));
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(ProjectList_cm.this, profile_cm.class));
    }
}
