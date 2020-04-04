package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

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

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.URL_STRINGS;

import be.project.sitereck.ProjectManager.Adapters.CmToProjectAdapter;
import be.project.sitereck.ProjectManager.POJO.AssignCmData;
import be.project.sitereck.R;

public class AssignCmToProject_PM extends AppCompatActivity implements View.OnClickListener , ItemClickListener {

    List<AssignCmData> data = new ArrayList<>();
    RecyclerView recyclerView;
    CmToProjectAdapter adapter;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_cm_to_project_pm);

        initData();
        sendRequest();
        //itemclick listner check?
        adapter = new CmToProjectAdapter(data,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void sendRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallCmList(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response.toString());
                    JSONArray jsonArray = object.getJSONArray("CM");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void initData() {
        recyclerView = findViewById(R.id.rcv_cm_assigntopm);
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onClick(View v, int adapterPosition) {

    }
}
