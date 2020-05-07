package be.project.sitereck.ProjectManager.Activities;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.project.sitereck.Construction_Manager.Activities.ProjectList_cm;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.Adapters.CMListAdapter_PM;
import be.project.sitereck.ProjectManager.Adapters.CmToProjectAdapter;
import be.project.sitereck.ProjectManager.POJO.AssignCmData;
import be.project.sitereck.ProjectManager.POJO.CMInfoDataClass;
import be.project.sitereck.R;

public class AllCmList_PM extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener, ItemClickListener{

    Toolbar toolbar;
    ImageView burgerimg;
    TextView toptitle;

    private RecyclerView recyclerView;
    CmToProjectAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<AssignCmData> listItems = new ArrayList<>();
    RequestQueue rq;
    AssignCmData data;
    TextView errormsg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cm_list_pm);

        toolbar = findViewById(R.id.toolbar);
        toptitle = findViewById(R.id.title_top);
        toptitle.setText("Construction Manager");
        burgerimg = findViewById(R.id.menu_icon);
        burgerimg.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

        recyclerView = findViewById(R.id.rec_CM_LIST);
        errormsg = findViewById(R.id.textemptycmlist);

        swipeRefreshLayout = findViewById(R.id.container_cmlist);
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

        burgerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void JSONCALL() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallCmList(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response ->" +response);;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("0")){
                        Toast.makeText(AllCmList_PM.this, "No Manager Found !!", Toast.LENGTH_LONG).show();
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
                Toast.makeText(AllCmList_PM.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(error.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);

    }

    @Override
    public void onRefresh() {
        listItems.clear();
        JSONCALL();
    }

    @Override
    public void onClick(View v, int adapterPosition) {
        try {
//            MakeDialog(adapterPosition);
            Intent intent = new Intent(AllCmList_PM.this, CMInfo.class);
            intent.putExtra("action","0");
            intent.putExtra("CmData",listItems.get(adapterPosition));
            startActivity(intent);
        }catch (Exception e){
            System.out.println("Exception -> "+ e);
            Toast.makeText(this, "Wait Your List Is Loading...", Toast.LENGTH_SHORT).show();
        }
    }
}
