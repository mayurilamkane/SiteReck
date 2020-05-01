package be.project.sitereck.Construction_Manager.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.project.sitereck.Construction_Manager.Adapters.ProjectListAdapter;
import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;
import be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.ProjectManager.Better.Activities.PM_ProjectList;
import be.project.sitereck.ProjectManager.Better.CustomMenu.SlideMenu;
import be.project.sitereck.ProjectManager.DialogGenerator;
import be.project.sitereck.R;
import de.hdodenhof.circleimageview.CircleImageView;

//import be.project.sitereck.MainActivity;

public class ProjectList_cm extends AppCompatActivity implements ItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    Toolbar toolbar;
    ImageView burgerimg;
    TextView toptitle;
    TextView errormsg, listfiltertext;
    ProjectDataClass data;
    String u_id = null;
    RelativeLayout filterMenuLayout;
    Dialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;
    ProjectListAdapter adapter;
    List<ProjectDataClass> listItems=new ArrayList<>();
    List<ProjectDataClass> compList=new ArrayList<>();
    List<ProjectDataClass> ongList=new ArrayList<>();
    List<ProjectDataClass> nonsList=new ArrayList<>();
    List<ProjectDataClass>  listToAdapter= new ArrayList<>();

    int currentList=1;
    DialogGenerator dialogGenerator;
    RequestQueue requestQueue;
    be.project.sitereck.GeneralClasses.SetSharedPrefrences prefrences = new be.project.sitereck.GeneralClasses.SetSharedPrefrences(this);
    //ProgressDialog progressDialog;
    String HTTP_JSON_URL = "https://sitereck-1.000webhostapp.com/API/CM/getProjectList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list_cm);
        recyclerView=(RecyclerView)findViewById(R.id.recProjList);
        toolbar = findViewById(R.id.toolbar);
        toptitle = findViewById(R.id.title_top);
        toptitle.setText("Project List");
        burgerimg = findViewById(R.id.menu_icon);
        filterMenuLayout = findViewById(R.id.layFilterlist);
        listfiltertext  = findViewById(R.id.textListFilter);
        dialogGenerator = new DialogGenerator(this);

        errormsg = findViewById(R.id.textEmptyProjList);

        System.out.println("user id ----> "+prefrences.getVar_User_id());
        // JSON_DATA_WEB_CALL();
        //  progressDialog=ProgressDialog.show(ProjectList_cm.this,"Please Wait","Loading List",true,false);

        swipeRefreshLayout = findViewById(R.id.containerProjList);
        swipeRefreshLayout.setOnRefreshListener(this);

        //swipeRefresh color scheme
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        adapter = new ProjectListAdapter(listItems,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                JSON_DATA_WEB_CALL();
            }
        });

        filterMenuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeDialog();
//                dialogGenerator.Generatedialog();
            }
        });

        burgerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProjectList_cm.this, "burger", Toast.LENGTH_SHORT).show();
//                FullDialog(PM_ProjectList.this,PM_ProjectList.this);
                SlideMenu slideMenu = new SlideMenu();
                ViewGroup viewGroup = findViewById(android.R.id.content);
                //slideMenu.FullDialog(ProjectList_cm.this,ProjectList_cm.this,viewGroup);

            }
        });


    }

    private void MakeDialog() {
        dialog = new Dialog(ProjectList_cm.this);
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
        final String u_id = prefrences.getVar_User_id();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTP_JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.i("tagconvertstr", "["+response+"]");
                    System.out.println("response --> "+response);
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("found").equals("0")){
                        Toast.makeText(ProjectList_cm.this, "No Project List is found", Toast.LENGTH_LONG).show();
                        errormsg.setVisibility(View.VISIBLE);
                    }
                    else if(jsonObject.getString("found").equals("1")) {
                        errormsg.setVisibility(View.GONE);
                        JSONArray array = jsonObject.getJSONArray("projects");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ProjectDataClass pd = new ProjectDataClass(object.getString("proj_name"), object.getString("proj_start_date"), object.getString("proj_end_date"), object.getString("proj_id"), object.getString("user_name"),object.getString("proj_status"));
                            listItems.add(pd);
                            GenerateFilterList(pd);
                            listToAdapter.add(pd);
                        }
                    }
                    //  if (progressDialog != null && progressDialog.isShowing())
                    //    progressDialog.dismiss();
                    SetFilterList(currentList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    //if (progressDialog != null && progressDialog.isShowing())
                    //  progressDialog.dismiss();
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProjectList_cm.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onRefresh() {
        //listItems.clear();
        //JSON_DATA_WEB_CALL();

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
        listItems.get(adapterPosition);
        createSharedPreference(adapterPosition);
        Intent intent = new Intent(ProjectList_cm.this, dashboard_cm.class);
        intent.putExtra("proj_id",listItems.get(adapterPosition).getId());
        startActivity(intent);
    }

    private void GenerateFilterList(ProjectDataClass data1){
        System.out.println("status ---> "+data1.getStatus());
        if (data1.getStatus().equals("0")){
            nonsList.add(data1);
        }else if(data1.getStatus().equals("1")){
            compList.add(data1);
        }else if (data1.getStatus().equals("2")){
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

    private void createSharedPreference(int adapterPosition) {
        new SetSharedPrefrences(this).setTitle(String.valueOf(listItems.get(adapterPosition).getTitle()));
        new SetSharedPrefrences(this).setStartDate(String.valueOf(listItems.get(adapterPosition).getStartDate()));
        new SetSharedPrefrences(this).setEndDate(String.valueOf(listItems.get(adapterPosition).getEndDate()));
        new SetSharedPrefrences(this).setProjId(String.valueOf(listItems.get(adapterPosition).getId()));

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(ProjectList_cm.this, profile_cm.class));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
