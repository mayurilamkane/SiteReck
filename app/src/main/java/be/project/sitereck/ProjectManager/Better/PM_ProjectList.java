package be.project.sitereck.ProjectManager.Better;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.Activities.AddNewProjectPM;
import be.project.sitereck.ProjectManager.Activities.ProjectDash;
import be.project.sitereck.ProjectManager.Adapters.ProjectListAdapterPm;
import be.project.sitereck.ProjectManager.POJO.ProjectData;
import be.project.sitereck.R;

public class PM_ProjectList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ItemClickListener{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ProjectListAdapterPm adapter;
    ProjectData data ;
    TextView errormsg, listfiltertext;
    FloatingActionButton fabAddProject;
    List<ProjectData> listItems = new ArrayList<>();
    RequestQueue rq ;
    String u_id = null;
    SetSharedPrefrences preferences = new SetSharedPrefrences(this);

    RelativeLayout filterMenuLayout ;
    Dialog dialog;
    List<ProjectData> compList = new ArrayList<>();
    List<ProjectData>    ongList = new ArrayList<>();
    List<ProjectData>    nonsList= new ArrayList<>();
    List<ProjectData>    listToAdapter= new ArrayList<>();
    int currentList = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmprojectlist);

        errormsg = findViewById(R.id.textemptyprojectlist);
        recyclerView = findViewById(R.id.rec_projList);
        filterMenuLayout = findViewById(R.id.lay_filterlist);
        listfiltertext  = findViewById(R.id.text_listfilter);
        fabAddProject = findViewById(R.id.fab_addproject);
        swipeRefreshLayout = findViewById(R.id.container_projList);
        swipeRefreshLayout.setOnRefreshListener(this);

        //swipeRefresh color scheme
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        //define adapter for list
        adapter = new ProjectListAdapterPm(listToAdapter,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //swiperefresh post method
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                JSON_DATA_WEB_CALL();
            }
        });

        //fab clicklistner
        fabAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addProjectIntent  = new Intent(PM_ProjectList.this, AddNewProjectPM.class);
                startActivity(addProjectIntent);
            }
        });

        filterMenuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showMenu(v);
                MakeDialog();
            }
        });
    }

    private void MakeDialog() {
        dialog = new Dialog(PM_ProjectList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_projectlist_filter);
        LinearLayout l1 = dialog.findViewById(R.id.ll);
        l1.setBackgroundResource(R.drawable.dialog_bg);
        TextView all, ong,comp,nons;
        all = dialog.findViewById(R.id.item_allproject);
        ong = dialog.findViewById(R.id.item_onngoing);
        comp = dialog.findViewById(R.id.item_completed);
        nons = dialog.findViewById(R.id.item_notstarted);
        dialog.show();
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFilterList(1);
                dialog.dismiss();
            }
        });
        ong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFilterList(2);
                dialog.dismiss();
            }
        });
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFilterList(3);
                dialog.dismiss();
            }
        });
        nons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFilterList(4);
                dialog.dismiss();
            }
        });

    }


    private void JSON_DATA_WEB_CALL() {
        swipeRefreshLayout.setRefreshing(true);
        u_id = preferences.getVar_User_id();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjList(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("found").equals("1")){
                        errormsg.setVisibility(View.GONE);
                        JSONArray array= jsonObject.getJSONArray("projects");
                        for (int i =0 ; i<array.length(); i++){
                            JSONObject object = array.getJSONObject(i);
                            data = new ProjectData(object.getString("proj_id"),
                                    object.getString("proj_name"),
                                    object.getString("proj_desc"),
                                    object.getString("proj_start_date"),
                                    object.getString("proj_end_date"),
                                    object.getString("proj_address"),
                                    object.getString("proj_lat"),
                                    object.getString("proj_long"),
                                    object.getString("proj_status"),
                                    object.getString("status_date"),
                                    object.getString("diff")
                            );
                            listItems.add(data);
                            listToAdapter.add(data);
                            GenerateFilterList(data);
                        }
                        SetFilterList(currentList);
                    }else if (jsonObject.getString("found").equals("0")){
                        Toast.makeText(PM_ProjectList.this, "SORRY YOU DONT HAVE ANY PROJECTS YET !", Toast.LENGTH_LONG).show();
                        errormsg.setVisibility(View.VISIBLE);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    System.out.println(e.toString());
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PM_ProjectList.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(error.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("u_id",u_id);
                return params;
            }
        };
        rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }


    @Override
    public void onRefresh() {
        listToAdapter.clear();
        listItems.clear();
        ongList.clear();
        compList.clear();
        nonsList.clear();
        JSON_DATA_WEB_CALL();
        SetFilterList(currentList);

    }

    @Override
    public void onClick(View v, int adapterPosition) {
        try {
            Intent intent = new Intent (PM_ProjectList.this, ProjectDash.class);
            intent.putExtra("ProjectData", (Serializable) listItems.get(adapterPosition));
            startActivity(intent);
        }catch (Exception e){
            System.out.println("Exception -> "+ e);
            Toast.makeText(this, "Wait Your List Is Loading...", Toast.LENGTH_SHORT).show();
        }
    }


    private void GenerateFilterList(ProjectData data1){
        System.out.println("status ---> "+data1.getProject_status());
        if (data1.getProject_status().equals("0")){
            nonsList.add(data1);
        }else if(data1.getProject_status().equals("1")){
            compList.add(data1);
        }else if (data1.getProject_status().equals("2")){
            ongList.add(data1);
        }
    }
    private void SetFilterList(int i) {
         if (i == 1){
            currentList = 1;
            listfiltertext.setText("All Projects");
            listToAdapter.clear();
            listToAdapter.addAll(listItems);
        }else if (i == 2){
            currentList = 2;
            listfiltertext.setText("OnGoing Projects");
            listToAdapter.clear();
            listToAdapter.addAll(ongList);
        }else if (i == 3){
            currentList = 3;
            listfiltertext.setText("completed Projects");
            listToAdapter.clear();
            listToAdapter.addAll(compList);
        }else if (i == 4){
            currentList = 4;
            listfiltertext.setText("Not Started Projects");
            listToAdapter.clear();
            listToAdapter.addAll(nonsList);
        }
        recyclerView.setAdapter(adapter);
    }
}
