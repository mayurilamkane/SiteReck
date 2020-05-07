package be.project.sitereck.ProjectManager.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.Adapters.CmToProjectAdapter;
import be.project.sitereck.ProjectManager.POJO.AssignCmData;
import be.project.sitereck.R;

public class ProjCMList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, ItemClickListener {

    Toolbar toolbar;
    ImageView burgerimg;
    TextView toptitle;

    private RecyclerView recyclerView;
    CmToProjectAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<AssignCmData> listItems = new ArrayList<>();
    RequestQueue rq;
    AssignCmData data;
    Dialog dialog;
    TextView errormsg ;
    ProgressDialog pd;
    String pid = "";
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj_cmlist);

        toolbar = findViewById(R.id.toolbar);
        toptitle = findViewById(R.id.title_top);
        toptitle.setText("Construction Manager");
        burgerimg = findViewById(R.id.menu_icon);
        burgerimg.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

        initData();
        swipeRefreshLayout = findViewById(R.id.container_cmlist);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        fab.setOnClickListener(this);
        Intent intent=getIntent();
        pid = intent.getStringExtra("pid");
        //
        //
        adapter = new CmToProjectAdapter(listItems,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                JSONCALL();
            }
        });

        burgerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        recyclerView = findViewById(R.id.rec_CM_LIST);
        fab = findViewById(R.id.fab_addcm);
        errormsg = findViewById(R.id.textemptycmlist);
    }


    private void JSONCALL() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjcmlist(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response ->" +response);;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("0")){
                        Toast.makeText(ProjCMList.this, "No Manager Found !!", Toast.LENGTH_LONG).show();
                        errormsg.setVisibility(View.VISIBLE);
                    }else if (jsonObject.getString("status").equals("1")) {
                        errormsg.setVisibility(View.GONE);
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
                Toast.makeText(ProjCMList.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRefresh(){
        listItems.clear();
        JSONCALL();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()  == R.id.fab_addcm){
            Intent intent1 = new Intent(this,AssignCmToProject_PM.class);
            intent1.putExtra("pid",pid);
            startActivity(intent1);
        }
    }

    @Override
    public void onClick(View v, final int adapterPosition) {

        try {
//            MakeDialog(adapterPosition);
            Intent intent = new Intent(ProjCMList.this, CMInfo.class);
            intent.putExtra("action","1");
            intent.putExtra("CmData",listItems.get(adapterPosition));
            startActivity(intent);
        }catch (Exception e){
            System.out.println("Exception -> "+ e);
            Toast.makeText(this, "Wait Your List Is Loading...", Toast.LENGTH_SHORT).show();
        }

    }

//    void MakeDialog(final int adapterPosition){
//        dialog = new Dialog(ProjCMList.this);
//        dialog.setContentView(R.layout.dialog_rem_cm);
//
//        TextView name , email,contact;
//        Button btncancel, btnremove;
//        name = dialog.findViewById(R.id.tvc_cmname);
//        email = dialog.findViewById(R.id.tvc_email);
//        contact = dialog.findViewById(R.id.tvc_contact);
//        btncancel = dialog.findViewById(R.id.btncmdialog_cancel);
//        btnremove = dialog.findViewById(R.id.btncmdialog_remove);
//
//        name.setText(listItems.get(adapterPosition).getName());
//        email.setText(listItems.get(adapterPosition).getEmail());
//        contact.setText(listItems.get(adapterPosition).getContact());
//
//        dialog.show();
//        btncancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        btnremove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RemoveCM(listItems.get(adapterPosition).getId());
//            }
//        });
//    }
//
//    private void RemoveCM(final String u_id) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjremcm(), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Log.i("tagconvertstr", "["+response+"]");
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getString("status").equals("1")){
//                        dialog.dismiss();
//                        Toast.makeText(ProjCMList.this, "Manager Removed From Project. Refreshing List.", Toast.LENGTH_LONG).show();
//                        onRefresh();
//                    }else{
//                        dialog.dismiss();
//                        Toast.makeText(ProjCMList.this, "Sometthing went wrong. Try Refreshing list and Try again. ", Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    dialog.dismiss();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(error.toString());
//                dialog.dismiss();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String , String> params = new HashMap<>();
//                params.put("proj_id",pid);
//                params.put("u_id",u_id);
//                return params;
//            }
//        };
//        rq= Volley.newRequestQueue(this);
//        rq.add(stringRequest);
//    }
}

