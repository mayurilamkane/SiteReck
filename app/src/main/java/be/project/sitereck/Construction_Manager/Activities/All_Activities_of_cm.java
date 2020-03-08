package be.project.sitereck.Construction_Manager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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

import be.project.sitereck.Construction_Manager.Adapters.Activity_Adapter_cm;
import be.project.sitereck.Construction_Manager.DataClass.Activity_dataClass_cm;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.R;

public class All_Activities_of_cm extends AppCompatActivity implements View.OnClickListener, ItemClickListener {
    private RecyclerView recyclerView;
    private CheckBox checkbox;
    Activity_Adapter_cm adapter_cm;
    List<Activity_dataClass_cm> list;
    RequestQueue requestQueue;
    String HTTP_JSON_URL = "https://sitereck-1.000webhostapp.com/API/getActivityList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__activities_of_cm);
        //Toast.makeText(this, "This Is On Going Project List", Toast.LENGTH_SHORT).show();
        list = new ArrayList<>();
        JSON_DATA_WEB_CALL();

        recyclerView=(RecyclerView)findViewById(R.id.recy_ptimeline);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter_cm = new Activity_Adapter_cm(this,list,this);
        //recyclerView.setHasFixedSize(true);

        //recyclerView.setAdapter(adapter_cm);


    }

    private void JSON_DATA_WEB_CALL() {
      //  recyclerView=(RecyclerView)findViewById(R.id.recy_ptimeline);
       // list.add(new Activity_dataClass_cm("title 1","status","1/1/1","1/1/1"));
      //  list.add(new Activity_dataClass_cm("title 2","status","1/1/1","1/1/1"));
     //   list.add(new Activity_dataClass_cm("title 3","status","1/1/1","1/1/1"));
       // list.add(new Activity_dataClass_cm("title 4","status","1/1/1","1/1/1"));
       // list.add(new Activity_dataClass_cm("title 5","status","1/1/1","1/1/1"));
        StringRequest stringRequest=new StringRequest(Request.Method.POST, HTTP_JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("projects");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Activity_dataClass_cm adc = new Activity_dataClass_cm(object.getString("act_name"), object.getString("p_act_start_date"), object.getString("p_act_end_date"), object.getInt("act_id"));
                        list.add(adc);
                    }
                    recyclerView.setAdapter(adapter_cm);
                } catch (JSONException e) {
                    Toast.makeText(All_Activities_of_cm.this, "" +e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(All_Activities_of_cm.this, "" +error.toString(), Toast.LENGTH_SHORT).show();


            }
        });
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v,int position) {
        list.get(position);
Intent intent =new Intent(All_Activities_of_cm.this, update_activity.class);
        startActivity(intent);
        //Toast.makeText(this, list.get(position).getTitle() +"  " + list.get(position).getId(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {

    }
}
