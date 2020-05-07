package be.project.sitereck.ProjectManager.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import be.project.sitereck.ProjectManager.POJO.ProjectMiscData;
import be.project.sitereck.R;

public class ActivityManager_PM extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, ItemClickListener {

    Toolbar toolbar;
    ImageView burgerimg;
    TextView toptitle;

    RelativeLayout filterMenuLayout ;

    List<ActivityManagerClass_PM> data = new ArrayList<>();
    List<ActivityManagerClass_PM> listToAdapter = new ArrayList<>();
    List<ActivityManagerClass_PM> comp = new ArrayList<>();
    List<ActivityManagerClass_PM> nonc = new ArrayList<>();
    RecyclerView recyclerView;
    ActivityMangerAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String pid = null;
    TextView errormsg, listfiltertext;
    FloatingActionButton fab_addActivity;
    Dialog dialog;
    Intent intent;
    SetSharedPrefrences preferences = new SetSharedPrefrences(this);
    RequestQueue requestQueue;

    int currentList = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__pm);

        toolbar = findViewById(R.id.toolbar);
        toptitle = findViewById(R.id.title_top);
        toptitle.setText("Activity Manager");
        burgerimg = findViewById(R.id.menu_icon);

        filterMenuLayout = findViewById(R.id.lay_filterlist);
        listfiltertext  = findViewById(R.id.text_listfilter);

        initData();
        fab_addActivity.setOnClickListener(this);
        swipeRefreshLayout = findViewById(R.id.container_activity_manager);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

//        sendRequest();
        adapter = new ActivityMangerAdapter(listToAdapter,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                sendRequest();
            }
        });
        filterMenuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeFilterDialog();
            }
        });

    }

    private void MakeFilterDialog() {
        dialog = new Dialog(ActivityManager_PM.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_actman_filter);
        LinearLayout l1 = dialog.findViewById(R.id.ll);
        l1.setBackgroundResource(R.drawable.dialog_bg);
        TextView all,comp,nons,proname;
        all = dialog.findViewById(R.id.item_allact);
        proname = dialog.findViewById(R.id.pronametitle);
        comp = dialog.findViewById(R.id.item_completedact);
        nons = dialog.findViewById(R.id.item_incompleteact);
        proname.setText(ProjectMiscData.getProject_name());
        dialog.show();
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFilterList(1);
                dialog.dismiss();
            }
        });
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFilterList(2);
                dialog.dismiss();
            }
        });
        nons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFilterList(3);
                dialog.dismiss();
            }
        });
    }

    private void SetFilterList(int i) {
        if (i == 1){
            currentList = 1;
            listfiltertext.setText("All Activities");
            listToAdapter.clear();
            listToAdapter.addAll(data);
            if (listToAdapter.size() == 0 ){
//                Toast.makeText(ActivityManager_PM.this, "No Activities Found", Toast.LENGTH_LONG).show();
                errormsg.setVisibility(View.VISIBLE);
                errormsg.setText("Nothing is here!!!");
            }

        }else if (i == 2){
            currentList = 2;
            listfiltertext.setText("Completed Activities");
            listToAdapter.clear();
            listToAdapter.addAll(comp);
            if (listToAdapter.size() == 0 ){
//                Toast.makeText(ActivityManager_PM.this, "No Activity Completed YET !", Toast.LENGTH_LONG).show();
                errormsg.setVisibility(View.VISIBLE);
                errormsg.setText("Nothing is here!!!");
            }
        }else if (i == 3) {
            currentList = 3;
            listfiltertext.setText("Incomplete Acivities");
            listToAdapter.clear();
            listToAdapter.addAll(nonc);
            if (listToAdapter.size() == 0) {
//                Toast.makeText(ActivityManager_PM.this, "No Incomplete Activities", Toast.LENGTH_LONG).show();
                errormsg.setVisibility(View.VISIBLE);
                errormsg.setText("Nothing is here!!!");
            }
        }
        recyclerView.setAdapter(adapter);

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
                            listToAdapter.add(amc);
                            GenerateFilterList(amc);
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pid",pid);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void GenerateFilterList(ActivityManagerClass_PM data1) {
        if (data1.getStatus().equals("0")){
            nonc.add(data1);
        }else if(data1.getStatus().equals("1")){
            comp.add(data1);
        }
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
        listToAdapter.clear();
        nonc.clear();
        comp.clear();
        sendRequest();
        SetFilterList(currentList);
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
            Intent intent = new Intent(ActivityManager_PM.this, ProjectActInfo.class);
            intent.putExtra("ActData", listToAdapter.get(position));
            startActivityForResult(intent, 2);
        }catch (Exception e){
            System.out.println("Exception -> "+ e);
            Toast.makeText(this, "Wait Your List Is Loading...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3){
            onRefresh();
        }else if (requestCode == 2){
//            onRefresh();
        }
    }
    //    @Override
//    public void onClick(View view, final int position) {
//        try {
//            if (data.get(position).getStatus().equals("1")){
//                Toast.makeText(this, "This Activity is Completed. Cannot edit or remove it.", Toast.LENGTH_SHORT).show();
//            }
//            else{
////                MakeDialog(position);
//            }
//        }catch (Exception e){
//            System.out.println("Exception -> "+ e);
//            Toast.makeText(this, "Wait Your List Is Loading...", Toast.LENGTH_SHORT).show();
//        }
//    }
//    void MakeDialog(final int position){
//        dialog = new Dialog(ActivityManager_PM.this);
//        dialog.setContentView(R.layout.dialog_remove_activity);
//
//        TextView dtitle,dstartdate,denddate,dstatus;
//        Button btncancel, btnremove;
//        dtitle = dialog.findViewById(R.id.tvc_activity_title);
//        dstatus = dialog.findViewById(R.id.tvc_status);
//        dstartdate = dialog.findViewById(R.id.tvc_startdate);
//        denddate = dialog.findViewById(R.id.tvc_enddate);
//        btncancel = dialog.findViewById(R.id.dra_btn_cancel);
//        btnremove = dialog.findViewById(R.id.dra_btn_remove);
//
//        dtitle.setText(data.get(position).getTitle());
//        if(data.get(position).getStatus().equals("0")){
//            dstatus.setText("NOT STARTED");
//        }else if(data.get(position).getStatus().equals("1")){
//            dstatus.setText("COMPLETED");
//        }else if(data.get(position).getStatus().equals("2"))
//            dstatus.setText("ONGOING");
////        dstatus.setText(data.get(position).getStatus());
//        dstartdate.setText(data.get(position).getStartDate());
//        denddate.setText(data.get(position).getEndDate());
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
//                Toast.makeText(ActivityManager_PM.this, "remove", Toast.LENGTH_SHORT).show();
//                RemoveActivity(data.get(position).getID(),position);
//            }
//        });
//    }

//    private void RemoveActivity(final String id , final int position) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjremact(), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Log.i("tagconvertstr", "["+response+"]");
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getString("status").equals("1")){
//                        dialog.dismiss();
//                        Toast.makeText(ActivityManager_PM.this, "Activity Removed From Project. Refreshing List.", Toast.LENGTH_LONG).show();
//                        onRefresh();
//                    }else{
//                        dialog.dismiss();
//                        Toast.makeText(ActivityManager_PM.this, "Sometthing went wrong. Try Refreshing list and Try again. ", Toast.LENGTH_LONG).show();
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
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("p_act_id",id);
//                params.put("pid",pid);
//                return params;
//            }
//        };
//        requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

}
