package be.project.sitereck.Construction_Manager.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.project.sitereck.Construction_Manager.Adapters.Activity_Adapter_cm;
import be.project.sitereck.Construction_Manager.DataClass.Activity_dataClass_cm;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.R;

public class All_Activities_of_cm extends AppCompatActivity implements View.OnClickListener, ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private CheckBox checkbox;
    Activity_Adapter_cm adapter_cm;
    List<Activity_dataClass_cm> list;
    RequestQueue requestQueue;
    be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences prefrences=new be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences(this);
    //ProgressDialog progressDialog;
    String HTTP_JSON_URL = "https://sitereck-1.000webhostapp.com/API/getActivityList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__activities_of_cm);
        //Toast.makeText(this, "This Is On Going Project List", Toast.LENGTH_SHORT).show();
        list = new ArrayList<>();
        //JSON_DATA_WEB_CALL();
        //progressDialog = ProgressDialog.show(All_Activities_of_cm.this,"Please Wait","Loading List",true,false);
        swipeRefreshLayout=findViewById(R.id.container_projList);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        recyclerView=(RecyclerView)findViewById(R.id.recy_ptimeline);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter_cm = new Activity_Adapter_cm(this,list,this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                JSON_DATA_WEB_CALL();
            }
        });
}

    private void JSON_DATA_WEB_CALL() {
        swipeRefreshLayout.setRefreshing(true);
        final String projId=prefrences.getProjectId();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, HTTP_JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("found").equals("0")) {
                        Toast.makeText(All_Activities_of_cm.this, "No Activity List is found", Toast.LENGTH_LONG).show();
                    } else if (jsonObject.getString("found").equals("1")) {
                        JSONArray array = jsonObject.getJSONArray("projects");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Activity_dataClass_cm adc = new Activity_dataClass_cm(object.getString("act_name"), object.getString("proj_status"), object.getString("p_act_start_date"), object.getString("p_act_end_date"));
                            list.add(adc);
                        }
                    }
                  //  if (progressDialog != null && progressDialog.isShowing())
                    //    progressDialog.dismiss();
                    recyclerView.setAdapter(adapter_cm);
                } catch (JSONException e) {
                    //if (progressDialog != null && progressDialog.isShowing())
                     //   progressDialog.dismiss();
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(All_Activities_of_cm.this, "" +error.toString(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("proj_id",projId);
                return params;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh() {
            list.clear();
        JSON_DATA_WEB_CALL();
    }

    @Override
    public void onClick(View v,int position) {
        list.get(position);
Intent intent =new Intent(All_Activities_of_cm.this, update_activity.class);
        startActivity(intent);
        //Toast.makeText(this, list.get(position).getTitle() +"  " + list.get(position).getId(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
