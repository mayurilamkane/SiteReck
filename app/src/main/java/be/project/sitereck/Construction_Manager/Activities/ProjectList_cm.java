package be.project.sitereck.Construction_Manager.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.Construction_Manager.Adapters.ProjectListAdapter;
import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
//import be.project.sitereck.MainActivity;
import be.project.sitereck.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectList_cm extends AppCompatActivity implements ItemClickListener,View.OnClickListener{
    private RecyclerView recyclerView;
    ProjectListAdapter adapter;
    List<ProjectDataClass> listItems;
    CircleImageView circleImageView;
    RequestQueue requestQueue;
    String HTTP_JSON_URL = "https://sitereck-1.000webhostapp.com/API/getProjectList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list_cm);
        JSON_DATA_WEB_CALL();
        listItems=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        circleImageView=(CircleImageView)findViewById(R.id.img);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProjectListAdapter(listItems,this,this);
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
                Intent intent = new Intent(ProjectList_cm.this, dashboard_cm.class);
                startActivity(intent);
    }

    @Override
    public void onClick(View v) {
//        startActivity(new Intent(ProjectList_cm.this,MainActivity.class));
    }
}
