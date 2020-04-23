package be.project.sitereck.ProjectManager.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import be.project.sitereck.Construction_Manager.Activities.ProjectList_cm;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.Adapters.CMListAdapter_PM;
import be.project.sitereck.ProjectManager.POJO.CMInfoDataClass;
import be.project.sitereck.R;

public class AllCmList_PM extends AppCompatActivity implements ItemClickListener {

    private RecyclerView recyclerView;
    CMListAdapter_PM adapter;
    List<CMInfoDataClass> listItems = new ArrayList<>();
    RequestQueue rq;
    CMInfoDataClass data;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cm_list_pm);
        //
        recyclerView = findViewById(R.id.rec_CM_LIST);
        //
        JSONCALL();
        //
        adapter = new CMListAdapter_PM(listItems,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void JSONCALL() {
        pd = ProgressDialog.show(this,null,"Loading Your List");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallCmList(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response ->" +response);;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("users");

                    for (int i =0; i <array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        data = new CMInfoDataClass(object.getString("user_name"), object.getString("user_email"), object.getString("user_contact"), object.getString("user_id"), object.getString("count"));
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
                Toast.makeText(AllCmList_PM.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v, int adapterPosition) {
        listItems.get(adapterPosition);
        Toast.makeText(this, "CM- "+ listItems.get(adapterPosition).getCMName(), Toast.LENGTH_SHORT).show();
        Intent intent  = new Intent(AllCmList_PM.this,CMInfo.class);
        intent.putExtra("name",listItems.get(adapterPosition).getCMName());
        intent.putExtra("email",listItems.get(adapterPosition).getEmail());
        intent.putExtra("contact",listItems.get(adapterPosition).getContact());
        intent.putExtra("id",listItems.get(adapterPosition).getId());
        startActivity(intent);
    }
}
