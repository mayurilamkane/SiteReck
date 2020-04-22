package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class CMProjList extends AppCompatActivity implements ItemClickListener {

    private String cmId;
    private RecyclerView recyclerView;
    CmProjectAdapter adapter ;
    List<DataForCardProject> list = new ArrayList<>();
    DataForCardProject data;
    RequestQueue rq;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmprojlist);

        recyclerView = findViewById(R.id.rec_prolist);
        Intent intent = getIntent();
        cmId = intent.getStringExtra("id");

        JSONCALL();
        adapter = new CmProjectAdapter(list,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void JSONCALL() {
        pd = ProgressDialog.show(this,null,"Loading Your List");
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL_STRINGS.getCallCmnonpro(), new Response.Listener<String>() {
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
                Toast.makeText(CMProjList.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    public void AddProjectDialog(View view, String title , final String pid){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage("Add this Project");
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Adding Project",
                                Toast.LENGTH_SHORT).show();
                        ADDPROJECTTOCM(pid);
                    }
                });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),
                        android.R.string.no, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void ADDPROJECTTOCM(final String pid) {
        pd = ProgressDialog.show(this,null,"Adding Project");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallAddcmtopro(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response ->" +response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    if (object.getString("success").equals("1")){
                        if(pd!=null && pd.isShowing())
                            pd.dismiss();
                        JSONCALL();
                        Toast.makeText(CMProjList.this, "Project Added", Toast.LENGTH_SHORT).show();
                    }else{
                        if(pd!=null && pd.isShowing())
                            pd.dismiss();

                        Toast.makeText(CMProjList.this, "Something Went Wrong Try Again", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
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
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("u_id",cmId);
                params.put("p_id",pid);
                return params;
            }
        };
        rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v, int adapterPosition) {
        list.get(adapterPosition);
        Toast.makeText(this, list.get(adapterPosition).getPname(), Toast.LENGTH_SHORT).show();
        AddProjectDialog(v,list.get(adapterPosition).getPname(),list.get(adapterPosition).getId());

    }
}
