package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import be.project.sitereck.ProjectManager.Adapters.MatReqAdapter;
import be.project.sitereck.ProjectManager.POJO.MatReqItemClass;
import be.project.sitereck.R;

public class MaterialRequestFromCm extends AppCompatActivity implements ItemClickListener {

    RecyclerView repolist;
    MatReqAdapter adapter;
    List<MatReqItemClass> list=new ArrayList<>();
    RequestQueue requestQueue;
    String pid;
    String HTTP_JSON_URL="https://sitereck-1.000webhostapp.com/API/getMaterialList.php";
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matreq_from_cm);
        Intent intent=getIntent();
        pid = intent.getStringExtra("pid");
        JSON_DATA_WEB_CALL();
        repolist=(RecyclerView)findViewById(R.id.rec_cmmrlist);
        repolist.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MatReqAdapter(list,this,this);

    }

    private void JSON_DATA_WEB_CALL() {
    //final String projId=prefrences.getProjectId();
        pd = ProgressDialog.show(this,null,"Loading Your List");
       System.out.println("project id -> " +pid);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, HTTP_JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //requestQueue.getCache().clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("activities");
                    System.out.println("array = "+array);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        MatReqItemClass r = new MatReqItemClass(object.getString("req_material"), object.getString("req_required_date"));
                        list.add(r);
//                        Toast.makeText(MaterialRequestListCM.this, object.getString("req_material"), Toast.LENGTH_SHORT).show();
                        System.out.println("list = "+ list.get(i));
                    }
                    if(pd!=null && pd.isShowing())
                        pd.dismiss();
                    repolist.setAdapter(adapter);
                } catch (JSONException e) {
                    if(pd!=null && pd.isShowing())
                        pd.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(pd!=null && pd.isShowing())
                    pd.dismiss();
                Toast.makeText(MaterialRequestFromCm.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("proj_id",pid);
                return params;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v, int adapterPosition) {

    }
}
