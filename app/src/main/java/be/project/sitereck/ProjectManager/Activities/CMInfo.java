package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import be.project.sitereck.ProjectManager.POJO.CmProjectAdapter;
import be.project.sitereck.ProjectManager.POJO.DataForCardProject;
import be.project.sitereck.R;

public class CMInfo extends AppCompatActivity implements ItemClickListener {

    private TextView name,number,email;
    private String cmId;
    private Button buttonAdd;
    private RecyclerView recyclerView;
    CmProjectAdapter adapter ;
    List<DataForCardProject> list = new ArrayList<>();
    DataForCardProject data;
    RequestQueue rq;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cm_info);
        initData();
        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        email.setText(intent.getStringExtra("email"));
        number.setText(intent.getStringExtra("contact"));
        cmId = intent.getStringExtra("id");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(CMInfo.this,CMProjList.class);
                intent.putExtra("id",cmId);
                startActivity(intent);

            }
        });

        JSONCALL();
        adapter = new CmProjectAdapter(list,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void JSONCALL() {
        pd = ProgressDialog.show(this,null,"Loading Your List");
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL_STRINGS.getCallCmpro(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response ->" +response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("projects");

                    for (int i =0; i <array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        data = new DataForCardProject(object.getString("proj_id"),object.getString("proj_name"),object.getString("proj_start_date"),object.getString("proj_end_date"));
                        list.add(data);
                    }
                    if(pd!=null && pd.isShowing())
                        pd.dismiss();
                    recyclerView.setAdapter(adapter);

                }catch (JSONException e){
                    e.printStackTrace();
                    if(pd!=null && pd.isShowing())
                        pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(pd!=null && pd.isShowing())
                    pd.dismiss();
                Toast.makeText(CMInfo.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("u_id",cmId);
                return params;
            }
        };
        rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    private void initData() {
        name = findViewById(R.id.tv_cmname);
        number = findViewById(R.id.tv_cmno);
        email = findViewById(R.id.tv_cmemail);
        buttonAdd = findViewById(R.id.btn_addproject);

        recyclerView = findViewById(R.id.rec_prolist);
    }

    @Override
    public void onClick(View v, int adapterPosition) {

    }
}
