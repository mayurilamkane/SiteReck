package be.project.sitereck.ProjectManager.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.POJO.AssignCmData;
import be.project.sitereck.ProjectManager.POJO.CmProjectAdapter;
import be.project.sitereck.ProjectManager.POJO.DataForCardProject;
import be.project.sitereck.ProjectManager.POJO.ProjectMiscData;
import be.project.sitereck.R;

public class CMInfo extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ImageView burgerimg;
    TextView toptitle;

    TextView cmname, cmemail, cmcontact;
    Button btn1, btn2;
    FloatingActionButton fabCall;
    LinearLayout lay_btn;
    AssignCmData data;
    int action = -1;
    RequestQueue rq;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cm_info);

        toolbar = findViewById(R.id.toolbar);
        toptitle = findViewById(R.id.title_top);
        toptitle.setText("Construction Manager");
        burgerimg = findViewById(R.id.menu_icon);
        burgerimg.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

        cmname = findViewById(R.id.tv_cmname);
        cmemail = findViewById(R.id.tv_cmemail);
        cmcontact = findViewById(R.id.tv_cmno);
        btn2 = findViewById(R.id.btn2);
        fabCall = findViewById(R.id.fabcallcm);

        Intent intent = getIntent();

        data = (AssignCmData) intent.getSerializableExtra("CmData");
        cmname.setText(data.getName());
        cmemail.setText(data.getEmail());
        cmcontact.setText(data.getContact());

        if (intent.getStringExtra("action").equals("1")) {
            action = 1;
            btn2.setVisibility(View.VISIBLE);
            btn2.setText("Remove");
            btn2.setTextColor(this.getColor(R.color.white));
            btn2.setBackground(this.getResources().getDrawable(R.drawable.custom_ripple_remove));

        } else if (intent.getStringExtra("action").equals("2")) {
            action = 2;
            btn2.setVisibility(View.VISIBLE);
            btn2.setText("Add");
            btn2.setTextColor(this.getColor(R.color.white));
            btn2.setBackground(this.getResources().getDrawable(R.drawable.custom_ripple_add));
        } else if (intent.getStringExtra("action").equals("0")) {
            action = 0;
            lay_btn.setVisibility(View.GONE);
        }
        fabCall.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn2:
                if (action == 1) {
                    RemoveCM(data.getId());
                } else if (action == 2) {
                    AddCm(data.getId());
                }
                break;
            case R.id.fabcallcm:
                MakeCall();
                break;
        }
    }

    private void MakeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + data.getContact()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    private void AddCm(final String id ) {
        progressDialog = ProgressDialog.show(CMInfo.this,null,"Assigning Manager",true,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallAddcmproj(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")){
                        Toast.makeText(CMInfo.this, "Construction Manager Added To Project. Refreshing List.", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(CMInfo.this, "Sometthing went wrong. Try Refreshing list and Try again. ", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                progressDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String , String> params = new HashMap<>();
                params.put("proj_id", ProjectMiscData.getProject_id());
                params.put("u_id",id);
                return params;
            }
        };
        rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    private void RemoveCM(final String id) {
        progressDialog = ProgressDialog.show(CMInfo.this,null,"Assigning Manager",true,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallProjremcm(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")){
                        Toast.makeText(CMInfo.this, "Manager Removed From Project", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(CMInfo.this, "Sometthing went wrong. ", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String , String> params = new HashMap<>();
                params.put("proj_id",ProjectMiscData.getProject_id());
                params.put("u_id",id);
                return params;
            }
        };
        rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }
}
