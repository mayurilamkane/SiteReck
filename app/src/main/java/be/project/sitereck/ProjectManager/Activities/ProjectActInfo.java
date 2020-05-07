package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.POJO.ActivityManagerClass_PM;
import be.project.sitereck.ProjectManager.POJO.ProjectData;
import be.project.sitereck.ProjectManager.POJO.ProjectMiscData;
import be.project.sitereck.R;

public class ProjectActInfo extends AppCompatActivity {
    Toolbar toolbar;
    ImageView burgerimg;
    TextView toptitle;

    TextView actname, proname, sdate, ddate, status, statdate;
    LinearLayout llstatdate;
    Button btnremact;
    ActivityManagerClass_PM data;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectactinfo);

        data = (ActivityManagerClass_PM) getIntent().getSerializableExtra("ActData");

        toolbar = findViewById(R.id.toolbar);
        toptitle = findViewById(R.id.title_top);
        toptitle.setText("Activity");
        burgerimg = findViewById(R.id.menu_icon);
        burgerimg.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

        actname = findViewById(R.id.tv_act_name);
        proname = findViewById(R.id.tv_projectName);
        sdate   = findViewById(R.id.tv_sdate);
        ddate   = findViewById(R.id.tv_enddate);
        status  = findViewById(R.id.tv_status);
        statdate = findViewById(R.id.tv_statday);
        llstatdate = findViewById(R.id.lay_statday);
        btnremact =  findViewById(R.id.dra_btn_remove);

        actname.setText(data.getTitle());
        proname.setText(ProjectMiscData.getProject_name());
        sdate.setText(data.getStartDate());
        ddate.setText(data.getEndDate());

        if (data.getStatus().equals("0")){
            status.setText("Incomplete");
            llstatdate.setVisibility(View.GONE);
        }else if (data.getStatus().equals("1")){
            status.setText("Completed");
            statdate.setText(data.getStatusDate());
            btnremact.setEnabled(false);
            btnremact.setText("Completed");
            btnremact.setBackground(this.getDrawable(R.drawable.disabled_btn));
            btnremact.setTextColor(this.getResources().getColor(R.color.grey_60));
        }

        burgerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
                setResult(2);
                finish();
            }
        });

        btnremact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveActivity(data.getID(),ProjectMiscData.getProject_id());
            }
        });
    }
    private void RemoveActivity(final String id , final String pid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjremact(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")){

                        Toast.makeText(ProjectActInfo.this, "Activity Removed From Project", Toast.LENGTH_LONG).show();
                        setResult(3);
                        finish();

                    }else{
                        setResult(3);
                        finish();
                        Toast.makeText(ProjectActInfo.this, "Somethings Went Wrong !!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("p_act_id",id);
                params.put("pid",pid);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
