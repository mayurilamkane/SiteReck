package be.project.sitereck.ProjectManager.Activities;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.URL_STRINGS;

import be.project.sitereck.ProjectManager.Adapters.ActivityMangerAdapter;
import be.project.sitereck.ProjectManager.POJO.ActivityManagerClass_PM;
import be.project.sitereck.R;

public class ActivityManager_PM extends AppCompatActivity implements View.OnClickListener, ItemClickListener {

    List<ActivityManagerClass_PM> data = new ArrayList<>();
    RecyclerView recyclerView;
    ActivityMangerAdapter adapter;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__pm);

//        requestQueue = Volley.newRequestQueue(this);
        initData();
        sendRequest();
        adapter = new ActivityMangerAdapter(data,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void sendRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjactlist(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response.toString());
                    JSONArray jsonArray = object.getJSONArray("projects");
                    for (int i =0 ;i<jsonArray.length();i++){
                        JSONObject object1 = jsonArray.getJSONObject(i);
                        ActivityManagerClass_PM amc = new ActivityManagerClass_PM(object1.getString("act_name"), object1.getString("p_act_start_date"),object1.getString("p_act_end_date") );
                        data.add(amc);
                    }
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
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
        recyclerView = findViewById(R.id.rcv_activitylist);

    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onClick(View view, int position) {

    }
}
