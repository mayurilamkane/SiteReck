package be.project.sitereck.ProjectManager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.GeneralClasses.URL_STRINGS;

import be.project.sitereck.ProjectManager.Adapters.ActivityMangerAdapter;
import be.project.sitereck.ProjectManager.POJO.ActivityManagerClass_PM;
import be.project.sitereck.R;

public class ActivityManager_PM extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, ItemClickListener {

    List<ActivityManagerClass_PM> data = new ArrayList<>();
    RecyclerView recyclerView;
    ActivityMangerAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String pid = null;
    Intent intent;
    SetSharedPrefrences preferences = new SetSharedPrefrences(this);
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__pm);

        initData();
        swipeRefreshLayout = findViewById(R.id.container_activity_manager);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

//        sendRequest();
        adapter = new ActivityMangerAdapter(data,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                sendRequest();
            }
        });
    }

    private void sendRequest() {
        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjactlist(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("0")){
                        Toast.makeText(ActivityManager_PM.this, "No Activities Found !!", Toast.LENGTH_LONG).show();
                    }else if (object.getString("status").equals("1")) {
                        JSONArray jsonArray = object.getJSONArray("activity");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            ActivityManagerClass_PM amc = new ActivityManagerClass_PM(object1.getString("act_name"),
                                    object1.getString("p_act_start_date"),
                                    object1.getString("p_act_end_date"));
                            data.add(amc);
                        }
                        recyclerView.setAdapter(adapter);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.toString());
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pid",pid);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void initData() {
        recyclerView = findViewById(R.id.rcv_activitylist);
       Intent  intent = getIntent();
         pid = intent.getStringExtra("pid");

    }
    @Override
    public void onRefresh() {
        data.clear();
        sendRequest();
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onClick(View view, int position) {

    }
}
