package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.URL_STRINGS;

import be.project.sitereck.ProjectManager.Adapters.CmToProjectAdapter;
import be.project.sitereck.ProjectManager.POJO.AssignCmData;
import be.project.sitereck.R;

public class AssignCmToProject_PM extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ItemClickListener {

    private RecyclerView recyclerView;
    CmToProjectAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<AssignCmData> listItems = new ArrayList<>();
    RequestQueue rq;
    AssignCmData data;
    Dialog dialog;
    TextView errormsg ;
    String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_cm_to_project_pm);

        initData();
        Intent intent=getIntent();
        pid = intent.getStringExtra("pid");
        swipeRefreshLayout = findViewById(R.id.container_acmlist);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        adapter = new CmToProjectAdapter(listItems,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                JSONCALL();
            }
        });
    }

    private void JSONCALL() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjnoncmlist(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response ->" +response);;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("0")){
                        Toast.makeText(AssignCmToProject_PM.this, "No Construction Manager Found !!", Toast.LENGTH_LONG).show();
                        errormsg.setText("No Construction Manager Found !!!");
                    }else if (jsonObject.getString("status").equals("1")) {

                        JSONArray array = jsonObject.getJSONArray("cm");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            data = new AssignCmData(object.getString("user_name"),
                                    object.getString("user_id"),
                                    object.getString("user_email"),
                                    object.getString("user_contact"));
                            listItems.add(data);
                        }

                        recyclerView.setAdapter(adapter);
                    }

                }catch (JSONException e){
                    e.printStackTrace();

                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AssignCmToProject_PM.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(error.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String , String > params = new HashMap<>();
                params.put("pid",pid);
                return params;
            }
        };
        rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    private void initData() {
        recyclerView = findViewById(R.id.rec_ACM_LIST);
        errormsg = findViewById(R.id.textemptyacmlist);

    }

    @Override
    public void onRefresh() {
        listItems.clear();
        JSONCALL();
    }

    @Override
    public void onClick(View v, int adapterPosition) {

        try {
            MakeDialog(adapterPosition);
        }catch (Exception e){
            System.out.println("Exception -> "+ e);
            Toast.makeText(this, "Wait Your List Is Loading...", Toast.LENGTH_SHORT).show();
        }

    }

    private void MakeDialog(final int adapterPosition) {
        dialog = new Dialog(AssignCmToProject_PM.this);
        dialog.setContentView(R.layout.dialog_add_cm);

        TextView name , email,contact;
        Button btncancel, btnadd;
        name = dialog.findViewById(R.id.tvc_cmname);
        email = dialog.findViewById(R.id.tvc_email);
        contact = dialog.findViewById(R.id.tvc_contact);
        btncancel = dialog.findViewById(R.id.btncmdialog_cancel);
        btnadd = dialog.findViewById(R.id.btncmdialog_add);

        name.setText(listItems.get(adapterPosition).getName());
        email.setText(listItems.get(adapterPosition).getEmail());
        contact.setText(listItems.get(adapterPosition).getContact());

        dialog.show();
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCm(listItems.get(adapterPosition).getId(), pid);
            }
        });
    }

    private void AddCm(final String id, final String pid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallAddcmproj(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")){
                        dialog.dismiss();
                        Toast.makeText(AssignCmToProject_PM.this, "Construction Manager Added To Project. Refreshing List.", Toast.LENGTH_LONG).show();
                        onRefresh();
                    }else{
                        dialog.dismiss();
                        Toast.makeText(AssignCmToProject_PM.this, "Sometthing went wrong. Try Refreshing list and Try again. ", Toast.LENGTH_LONG).show();
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
                Map<String , String> params = new HashMap<>();
                params.put("proj_id",pid);
                params.put("u_id",id);
                return params;
            }
        };
        rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }
}
