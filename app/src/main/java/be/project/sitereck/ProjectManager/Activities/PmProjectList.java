package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.project.sitereck.Construction_Manager.Activities.ProjectList_cm;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.POJO.ProjectData;
import be.project.sitereck.ProjectManager.Adapters.ProjectListAdapterPm;
import be.project.sitereck.R;

public class PmProjectList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ItemClickListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ProjectListAdapterPm adapter;
    ProjectData data ;
    TextView errormsg;
    FloatingActionButton fabAddProject;
    List<ProjectData> listItems = new ArrayList<>();
    RequestQueue rq ;
    String u_id = null;
    SetSharedPrefrences preferences = new SetSharedPrefrences(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm_project_list);

        errormsg = findViewById(R.id.textemptyprojectlist);
        recyclerView = findViewById(R.id.rec_projList);
        fabAddProject = findViewById(R.id.fab_addproject);
        swipeRefreshLayout = findViewById(R.id.container_projList);
        swipeRefreshLayout.setOnRefreshListener(this);

        //swipeRefresh color scheme
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        //define adapter for list
        adapter = new ProjectListAdapterPm(listItems,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //swiperefresh post method
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                JSON_DATA_WEB_CALL();
            }
        });

        //fab clicklistner
        fabAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addProjectIntent  = new Intent(PmProjectList.this,AddNewProjectPM.class);
                startActivity(addProjectIntent);
            }
        });
    }

    private void JSON_DATA_WEB_CALL() {
        swipeRefreshLayout.setRefreshing(true);
        u_id = preferences.getVar_User_id();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjList(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("found").equals("1")){
                        errormsg.setVisibility(View.GONE);
                        JSONArray array= jsonObject.getJSONArray("projects");
                        for (int i =0 ; i<array.length(); i++){
                         JSONObject object = array.getJSONObject(i);
                         data = new ProjectData(object.getString("proj_id"),
                                 object.getString("proj_name"),
                                 object.getString("proj_desc"),
                                 object.getString("proj_start_date"),
                                 object.getString("proj_end_date"),
                                 object.getString("proj_address"),
                                 object.getString("proj_lat"),
                                 object.getString("proj_long"),
                                 object.getString("proj_status")
                                 );
                         listItems.add(data);
                        }
                        recyclerView.setAdapter(adapter);
                    }else if (jsonObject.getString("found").equals("0")){
                        Toast.makeText(PmProjectList.this, "SORRY YOU DONT HAVE ANY PROJECTS YET !", Toast.LENGTH_LONG).show();
                        errormsg.setVisibility(View.VISIBLE);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    System.out.println(e.toString());
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PmProjectList.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(error.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("u_id",u_id);
                return params;
            }
        };
        rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        listItems.clear();
        JSON_DATA_WEB_CALL();
    }

    @Override
    public void onClick(View v, int adapterPosition) {
        try {
            Intent intent = new Intent (PmProjectList.this,ProjectDash.class);
            intent.putExtra("ProjectData", (Serializable) listItems.get(adapterPosition));
            startActivity(intent);
        }catch (Exception e){
            System.out.println("Exception -> "+ e);
            Toast.makeText(this, "Wait Your List Is Loading...", Toast.LENGTH_SHORT).show();
        }
    }
}
