package be.project.sitereck.ProjectManager.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
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
    TextView errormsg ;
    FloatingActionButton fab_addActivity;
    Dialog dialog;
    Intent intent;
    SetSharedPrefrences preferences = new SetSharedPrefrences(this);
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__pm);

        initData();
        fab_addActivity.setOnClickListener(this);
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
                        errormsg.setVisibility(View.VISIBLE);
                    }else if (object.getString("status").equals("1")) {
                        errormsg.setVisibility(View.GONE);
                        JSONArray jsonArray = object.getJSONArray("activity");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            ActivityManagerClass_PM amc = new ActivityManagerClass_PM(object1.getString("proj_status"),
                                    object1.getString("act_name"),
                                    object1.getString("p_act_start_date"),
                                    object1.getString("p_act_end_date"),
                                    object1.getString("p_status_date"),
                                    object1.getString("p_act_id"));
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
        fab_addActivity = findViewById(R.id.fab_addactivity);
        errormsg = findViewById(R.id.textemptyactivitylist);
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
        if (v.getId()  == R.id.fab_addactivity){
            Intent intent =  new Intent(ActivityManager_PM.this, AddProjectActivityPM.class);
            intent.putExtra("pid",pid);
            startActivity(intent);
        }
    }


    @Override
    public void onClick(View view, final int position) {
        try {
            if (data.get(position).getStatus().equals("1")){
                Toast.makeText(this, "This Activity is Completed. Cannot edit or remove it.", Toast.LENGTH_SHORT).show();
            }
            else{
                MakeDialog(position);}
        }catch (Exception e){
            System.out.println("Exception -> "+ e);
            Toast.makeText(this, "Wait Your List Is Loading...", Toast.LENGTH_SHORT).show();
        }
    }
    void MakeDialog(final int position){
        dialog = new Dialog(ActivityManager_PM.this);
        dialog.setContentView(R.layout.dialog_remove_activity);

        TextView dtitle,dstartdate,denddate,dstatus;
        Button btncancel, btnremove;
        dtitle = dialog.findViewById(R.id.tvc_activity_title);
        dstatus = dialog.findViewById(R.id.tvc_status);
        dstartdate = dialog.findViewById(R.id.tvc_startdate);
        denddate = dialog.findViewById(R.id.tvc_enddate);
        btncancel = dialog.findViewById(R.id.dra_btn_cancel);
        btnremove = dialog.findViewById(R.id.dra_btn_remove);

        dtitle.setText(data.get(position).getTitle());
        if(data.get(position).getStatus().equals("0")){
            dstatus.setText("NOT STARTED");
        }else if(data.get(position).getStatus().equals("1")){
            dstatus.setText("COMPLETED");
        }else if(data.get(position).getStatus().equals("2"))
            dstatus.setText("ONGOING");
//        dstatus.setText(data.get(position).getStatus());
        dstartdate.setText(data.get(position).getStartDate());
        denddate.setText(data.get(position).getEndDate());

        dialog.show();
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityManager_PM.this, "remove", Toast.LENGTH_SHORT).show();
                RemoveActivity(data.get(position).getID(),position);
            }
        });
    }

    private void RemoveActivity(final String id , final int position) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjremact(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")){
                        dialog.dismiss();
                        Toast.makeText(ActivityManager_PM.this, "Activity Removed From Project. Refreshing List.", Toast.LENGTH_LONG).show();
                        onRefresh();
                    }else{
                        dialog.dismiss();
                        Toast.makeText(ActivityManager_PM.this, "Sometthing went wrong. Try Refreshing list and Try again. ", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                dialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("p_act_id",id);
                params.put("pid",pid);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
