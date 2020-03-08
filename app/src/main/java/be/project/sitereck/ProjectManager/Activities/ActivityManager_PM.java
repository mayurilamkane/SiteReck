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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallActivitylistPm(), new Response.Listener<String>() {
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
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL.getCallActivitylistPm(), null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                try {
//                    JSONObject object = new JSONObject(response.toString());
//                    JSONArray jsonArray = object.getJSONArray("projects");
//                    for (int i =0 ;i<jsonArray.length();i++){
//                        JSONObject object1 = jsonArray.getJSONObject(i);
//                        ActivityManagerClass_PM amc = new ActivityManagerClass_PM(object1.getString("act_name"), object1.getString("p_act_start_date"),object1.getString("p_act_end_date") );
//                        data.add(amc);
//                    }
//                    recyclerView.setAdapter(adapter);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////                for (int i =0 ; i<response.length();i++){
////                    ActivityManagerClass_PM managerClass_pm= new ActivityManagerClass_PM();
////                    try {
////                        JSONObject  object = response.getJSONObject(i);
////
////                        managerClass_pm.setTitle(object.getString("act_name"));
////                        managerClass_pm.setStartDate(object.getString("p_act_start_date"));
////                        managerClass_pm.setEndDate(object.getString("p_act_end_date"));
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                    data.add(managerClass_pm);
////
////                }
////                recyclerView.setAdapter(adapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ActivityManager_PM.this, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
    }

    private void initData() {
        recyclerView = findViewById(R.id.rcv_activitylist);
//        data.add(new ActivityManagerClass_PM("sitereck","02/03/2019","03/03/2019"));
//        data.add(new ActivityManagerClass_PM("sitereck","02/03/2019","03/03/2019","100%"));
//        data.add(new ActivityManagerClass_PM("sitereck","02/03/2019","03/03/2019","100%"));
//        data.add(new ActivityManagerClass_PM("sitereck","02/03/2019","03/03/2019","100%"));
//        data.add(new ActivityManagerClass_PM("sitereck","02/03/2019","03/03/2019","100%"));

    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onClick(View view, int position) {

    }
}
