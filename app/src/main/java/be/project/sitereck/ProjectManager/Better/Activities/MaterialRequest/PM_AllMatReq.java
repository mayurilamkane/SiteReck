package be.project.sitereck.ProjectManager.Better.Activities.MaterialRequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.Better.CustomMenu.SlideMenu;
import be.project.sitereck.ProjectManager.POJO.MatReqItemClass;
import be.project.sitereck.ProjectManager.POJO.PmMiscData;
import static be.project.sitereck.ProjectManager.POJO.PmMiscData.getProjectlist;

import be.project.sitereck.ProjectManager.POJO.ProjectMiscData;
import be.project.sitereck.R;

public class PM_AllMatReq extends AppCompatActivity  implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, ItemClickListener {

    //UI declaration
    RelativeLayout lay_reqfilter;
    ImageView burgerimg, filterimg;
    TextView toptitle, errormsg, filterProname;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    //filter dialog
    Dialog dialog;
    //nested list data
    List<MatReqItemClass> listToAdapter = new ArrayList<>();
    List<MatReqItemClass> allitemlist = new ArrayList<>();
    List<MatReqItemClass> approveditemlist = new ArrayList<>();
    List<MatReqItemClass> pendingitemlist = new ArrayList<>();

    HashMap <String , List<MatReqItemClass> > mapfordata  = new HashMap<>();

    //    sharedpreferences
    SetSharedPrefrences prefrences = new SetSharedPrefrences(this);
    private String u_id = null;

    //Volley
    MatReqItemClass data;
    RequestQueue rq;

    //recyclerviewadapter
    MatReqAdapter adapter ;

    String pid;
    int currentList = 1;
    String filterlisthead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmallmatreq);
        Intent i  = getIntent();
        pid = i.getStringExtra("pid");
        //HashMap create
        for (Map.Entry<String, String> prlist : getProjectlist().entrySet()){
            mapfordata.put(prlist.getKey(), allitemlist);
        }
        System.out.println(mapfordata);
        //UI declaration
        filterimg = findViewById(R.id.filterimg); filterimg.setVisibility(View.INVISIBLE);
        lay_reqfilter = findViewById(R.id.lay_req_filterlist);
        filterProname  = findViewById(R.id.text_req_listfilter);
        toptitle = findViewById(R.id.title_top);    toptitle.setText("Material Requests");

        burgerimg = findViewById(R.id.menu_icon);
        burgerimg.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

        errormsg = findViewById(R.id.textemptymatreq);
        swipeRefreshLayout = findViewById(R.id.container_reqList);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.rec_matreq);

        //top title
        if(pid.equals("-1")){
            filterlisthead = "All Project";
            filterProname.setText(filterlisthead+" - All Requests");
        }else {

            filterlisthead = ProjectMiscData.getProject_name();
            filterProname.setText(filterlisthead+" - All Requests");
        }

        //set recyclerview and adapter
        adapter = new MatReqAdapter(listToAdapter,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //swipeRefresh color scheme
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        //swiperefresh post method
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                if(pid.equals("-1")){
                 JSON_DATA_WEB_CALL();
                }else {
                    PROJECT_REQ();
                }
            }
        });



        //UI clicklistners
        lay_reqfilter.setOnClickListener(this);
        burgerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(PM_AllMatReq.this, "burger", Toast.LENGTH_SHORT).show();
//                CreateSlideMenu();
                finish();
            }
        });
    }

    private void PROJECT_REQ() {
        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallMatreqproj(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("success").equals("1")){

                        errormsg.setVisibility(View.GONE);
                        JSONArray array= jsonObject.getJSONArray("requests");

                        for (int i = 0 ; i<array.length(); i++){
                            JSONObject object = array.getJSONObject(i);
                            data = new MatReqItemClass(object.getString("req_id"),
                                    object.getString("proj_id"),
                                    object.getString("req_date"),
                                    object.getString("req_required_date"),
                                    object.getString("req_material"),
                                    object.getString("req_status"),
                                    object.getString("note")
                            );
                            allitemlist.add(data);
                            listToAdapter.add(data);
                            GenerateFilterList(data);
                        }
                        SetFilterList(currentList);
                        recyclerView.setAdapter(adapter);
                    }else if(jsonObject.getString("success").equals("0")){
//                        Toast.makeText(PM_AllMatReq.this, "There Are No Requests YET !", Toast.LENGTH_LONG).show();
                        errormsg.setVisibility(View.VISIBLE);
                        errormsg.setText("There Are No Requests YET !");
                    }
                }catch (Exception e){
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
                Map<String,String> params = new HashMap<>();
                params.put("proj_id",ProjectMiscData.getProject_id());
                return params;
            }
        };
        rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    private void JSON_DATA_WEB_CALL() {
        swipeRefreshLayout.setRefreshing(true);
        u_id = prefrences.getVar_User_id();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCALL_AllMatReq(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("success").equals("1")){

                        errormsg.setVisibility(View.GONE);
                        JSONArray array= jsonObject.getJSONArray("requests");

                        for (int i = 0 ; i<array.length(); i++){
                            JSONObject object = array.getJSONObject(i);
                            data = new MatReqItemClass(object.getString("req_id"),
                                    object.getString("proj_id"),
                                    object.getString("req_date"),
                                    object.getString("req_required_date"),
                                    object.getString("req_material"),
                                    object.getString("req_status"),
                                    object.getString("note")
                            );
                            allitemlist.add(data);
                            listToAdapter.add(data);
                            GenerateFilterList(data);
                        }
                        SetFilterList(currentList);
                        recyclerView.setAdapter(adapter);
                    }else if(jsonObject.getString("success").equals("0")){
                        Toast.makeText(PM_AllMatReq.this, "There Are No Requests YET !", Toast.LENGTH_LONG).show();
                        errormsg.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
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
                Map<String,String> params = new HashMap<>();
                params.put("u_id",u_id);
                return params;
            }
        };
        rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    private void GenerateFilterList(MatReqItemClass data) {
        if (data.getRStat().equals("0")){
            pendingitemlist.add(data);
        }else if (data.getRStat().equals("1")){
            approveditemlist.add(data);
        }
    }

    private void getallmap() {
        for (Map.Entry<String, List<MatReqItemClass> > prlist : mapfordata.entrySet()){
            System.out.println("printing map = "+ prlist.getKey());
            System.out.println(" map size = "+ prlist.getValue().size());
//            System.out.println("printing map = "+ prlist.getValue().get());
            for (int i = 0 ; i<prlist.getValue().size(); i++){
//                System.out.println(" list = "+req.getPId()+" "+req.getSmaterialName());
                System.out.println(prlist.getValue().get(i).getAll());
            }
        }
    }

    private void FilterDataForMap(MatReqItemClass data) {
        System.out.println(data.getPId());
        List<MatReqItemClass> tlist  = mapfordata.get(data.getPId());
        tlist.add(data);
        mapfordata.put(data.getPId(),tlist);
//        titemlist.clear();
//        titemlist = (mapfordata.get(data.getPId()));
//        titemlist.add(data);

//        mapfordata.get(data.getPId()).add(data);
//        System.out.println("pid ="+titemlist.get(0).getPId());
//        mapfordata.get(data.getPId()).add(data);
//        System.out.println("key = "+data.getPId()+" --> value =  "+data.getPId());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lay_req_filterlist:
                MakeFilterDialog();
                break;

        }
    }

    private void CreateSlideMenu() {
        SlideMenu slideMenu = new SlideMenu();
        ViewGroup viewGroup = findViewById(android.R.id.content);
        slideMenu.FullDialog(PM_AllMatReq.this,PM_AllMatReq.this,viewGroup);
    }

    private void MakeFilterDialog() {
        dialog = new Dialog(PM_AllMatReq.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_matreq_filter);
        LinearLayout l1 = dialog.findViewById(R.id.ll);
        l1.setBackgroundResource(R.drawable.dialog_bg);
        dialog.show();
        dialog.setCancelable(true);
        TextView proname , all , approved, pending;
//        final ListView listView ;
//        listView = dialog.findViewById(R.id.list_proname);

//        final String[] valuelist  = getProjectlist().values().toArray(new String[0]);
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.item_proname, valuelist);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(PM_AllMatReq.this, "name -"+valuelist[position], Toast.LENGTH_SHORT).show();
//            }
//        });
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
            filterProname.setText(filterlisthead+" - All Requests");
            listToAdapter.clear();
            listToAdapter.addAll(allitemlist);
            if (listToAdapter.size() == 0){
                errormsg.setVisibility(View.VISIBLE);
                errormsg.setText("There Are No Requests YET !");
            }
        }else if (i == 2){
            currentList = 2;
            filterProname.setText(filterlisthead+" - Approved Requests");
            listToAdapter.clear();
            listToAdapter.addAll(approveditemlist);
            if (listToAdapter.size() == 0){
                errormsg.setVisibility(View.VISIBLE);
                errormsg.setText("There Are No Approved Requests YET !");
            }
        }else if (i == 3){
            currentList = 3;
            filterProname.setText(filterlisthead+" - Pending Requests");
            listToAdapter.clear();
            listToAdapter.addAll(pendingitemlist);
            if (listToAdapter.size() == 0){
                errormsg.setVisibility(View.VISIBLE);
                errormsg.setText("There Are No Pending Requests YET !");
            }
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        allitemlist.clear();
        listToAdapter.clear();
        pendingitemlist.clear();
        approveditemlist.clear();
        if(pid.equals("-1")){
            JSON_DATA_WEB_CALL();
        }else {
            PROJECT_REQ();
        }
    }

    @Override
    public void onClick(View v, int adapterPosition) {
        MatReqDialog  makeDialog = new MatReqDialog();
        ViewGroup viewGroup = findViewById(android.R.id.content);
        makeDialog.FullMatReqDialog(PM_AllMatReq.this, PM_AllMatReq.this,viewGroup,allitemlist.get(adapterPosition), adapterPosition);
    }
}
