package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import java.util.List;

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.Adapters.CMListAdapter_PM;
import be.project.sitereck.ProjectManager.Adapters.CmToProjectAdapter;
import be.project.sitereck.ProjectManager.POJO.AssignCmData;
import be.project.sitereck.ProjectManager.POJO.CMInfoDataClass;
import be.project.sitereck.R;

public class ProjCMList extends AppCompatActivity  {

    private RecyclerView recyclerView;
    CmToProjectAdapter adapter;
    List<AssignCmData> listItems = new ArrayList<>();
    RequestQueue rq;
    AssignCmData data;
    ProgressDialog pd;
    int pid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj_cmlist);
        recyclerView = findViewById(R.id.rec_CM_LIST);
        //
        JSONCALL();
        //
        adapter = new CmToProjectAdapter(listItems,this,pid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void JSONCALL() {
        pd = ProgressDialog.show(this,null,"Loading Your List");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjcmlist(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response ->" +response);;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("users");

                    for (int i =0; i <array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        data = new AssignCmData(object.getString("user_name"), object.getString("user_id"));
                        listItems.add(data);
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
                Toast.makeText(ProjCMList.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }


}
