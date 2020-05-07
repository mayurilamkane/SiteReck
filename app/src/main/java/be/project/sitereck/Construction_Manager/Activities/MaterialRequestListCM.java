package be.project.sitereck.Construction_Manager.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import be.project.sitereck.Construction_Manager.Adapters.MaterialListAdapter;
import be.project.sitereck.Construction_Manager.DataClass.RequestitemClass_CM;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralActivities.MainActivity;
import be.project.sitereck.ProjectManager.DialogGenerator;
import be.project.sitereck.R;

public class MaterialRequestListCM extends AppCompatActivity implements ItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView repolist;
    Toolbar toolbar;
    ImageView burgerimg, filterimg;
    TextView toptitle;
    TextView errormsg, listfiltertext;
    MaterialListAdapter adapter;
    RelativeLayout filterMenuLayout;
    Dialog dialog;
    RequestitemClass_CM requestitemClass_cm;
    List<RequestitemClass_CM> list=new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    List<RequestitemClass_CM> allitemlist=new ArrayList<>();
    List<RequestitemClass_CM> approveditemlist=new ArrayList<>();
    List<RequestitemClass_CM> pendingitemlist=new ArrayList<>();
    RequestQueue requestQueue;
    be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences prefrences=new be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences(this);
    //ProgressDialog progressDialog;
    int currentList=1;
    String filterlisthead = "";
    String HTTP_JSON_URL="https://sitereck-1.000webhostapp.com/API/getMaterialList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_request_list_cm);
        toolbar = findViewById(R.id.toolbar);
        filterimg = findViewById(R.id.filterimg); filterimg.setVisibility(View.INVISIBLE);
        toptitle = findViewById(R.id.title_top);
        toptitle.setText("Material Request List ");
        burgerimg = findViewById(R.id.menu_icon);
        burgerimg.setVisibility(View.INVISIBLE);
//        burgerimg.setImageDrawable(this.getResources().getDrawable(R.drawable.profile));
        filterMenuLayout = findViewById(R.id.Filter);
        listfiltertext  = findViewById(R.id.tv_projecttitle);


        errormsg = findViewById(R.id.EmptyMatList);
//        requestQueue.getCache().clear();
        //JSON_DATA_WEB_CALL();
       // progressDialog=ProgressDialog.show(MaterialRequestListCM.this,"Please Wait","Loading List",false,true);
        swipeRefreshLayout = findViewById(R.id.container_projList);
        swipeRefreshLayout.setOnRefreshListener(this);

        //swipeRefresh color scheme
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        repolist=(RecyclerView)findViewById(R.id.repolist);
        repolist.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MaterialListAdapter(list,this,this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                JSON_DATA_WEB_CALL();
            }
        });
        filterMenuLayout.setOnClickListener(this);

    }

    private void JSON_DATA_WEB_CALL() {
        swipeRefreshLayout.setRefreshing(true);
        final String projId=prefrences.getProjectId();
        System.out.println("Project id -> "+projId);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, HTTP_JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //requestQueue.getCache().clear();
                    //Log.i("tagconvertstr", "["+response+"]");
                    System.out.println("response --> "+response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("success").equals("0")) {
                        System.out.println("no data found---->>");
                        Toast.makeText(MaterialRequestListCM.this, "No Material List Found", Toast.LENGTH_LONG).show();
                        errormsg.setVisibility(View.VISIBLE);
                    } else if (jsonObject.getString("success").equals("1")) {
                        errormsg.setVisibility(View.GONE);
                        JSONArray array = jsonObject.getJSONArray("activities");
                    //System.out.println("array = "+array);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        RequestitemClass_CM r = new RequestitemClass_CM(object.getString("req_material"), object.getString("req_required_date"), object.getString("req_status"));
                        list.add(r);
                        allitemlist.add(r);
                        GenerateFilterList(r);
//                        Toast.makeText(MaterialRequestListCM.this, object.getString("req_material"), Toast.LENGTH_SHORT).show();
                      //  System.out.println("list = "+ list.get(i));
                    }
                }
           //     if (progressDialog != null && progressDialog.isShowing())
             //       progressDialog.dismiss();
                    SetFilterList(currentList);
                repolist.setAdapter(adapter);
            } catch (JSONException e) {
               // if (progressDialog != null && progressDialog.isShowing())
                 //   progressDialog.dismiss();
                e.printStackTrace();
            }
                swipeRefreshLayout.setRefreshing(false);
        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MaterialRequestListCM.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    private void GenerateFilterList(RequestitemClass_CM data) {
        if (data.getStatus().equals("0")){
            pendingitemlist.add(data);
        }else if (data.getStatus().equals("1")){
            approveditemlist.add(data);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Filter:
                MakeFilterDialog();
                break;
        }
    }

    private void MakeFilterDialog() {
        dialog = new Dialog(MaterialRequestListCM.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_matreq_filter);
        LinearLayout l1 = dialog.findViewById(R.id.ll);
        l1.setBackgroundResource(R.drawable.dialog_bg);
        dialog.show();
        dialog.setCancelable(true);
        TextView proname , all , approved, pending;
        proname = dialog.findViewById(R.id.pronametitle);
        all = dialog.findViewById(R.id.item_allreq);
        approved = dialog.findViewById(R.id.item_aapprovedreq);
        pending = dialog.findViewById(R.id.item_pendingreq);

        proname.setText(filterlisthead);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFilterList(1);
                dialog.dismiss();
            }
        });
        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFilterList(2);
                dialog.dismiss();
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
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
            listfiltertext.setText(filterlisthead+"  All Requests");
            list.clear();
            list.addAll(allitemlist);
            if (list.size() == 0){
                errormsg.setVisibility(View.VISIBLE);
                errormsg.setText("There Are No Requests YET !");
            }
        }else if (i == 2){
            currentList = 2;
            listfiltertext.setText(filterlisthead+"  Approved Requests");
            list.clear();
            list.addAll(approveditemlist);
            if (list.size() == 0){
                errormsg.setVisibility(View.VISIBLE);
                errormsg.setText("There Are No Approved Requests YET !");
            }
        }else if (i == 3){
            currentList = 3;
            listfiltertext.setText(filterlisthead+"  Pending Requests");
            list.clear();
            list.addAll(pendingitemlist);
            if (list.size() == 0){
                errormsg.setVisibility(View.VISIBLE);
                errormsg.setText("There Are No Pending Requests YET !");
            }
        }
        repolist.setAdapter(adapter);
    }


    @Override
    public void onRefresh() {
    list.clear();
    allitemlist.clear();
    pendingitemlist.clear();
    approveditemlist.clear();
    JSON_DATA_WEB_CALL();
    }

    @Override
    public void onClick(View v, int adapterPosition) {
          //  list.get(adapterPosition);
        //Intent intent=new Intent();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
