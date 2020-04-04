package be.project.sitereck.Construction_Manager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import be.project.sitereck.R;

public class material_Request_CM extends AppCompatActivity implements View.OnClickListener {

    public EditText edmaterial, edate;
    Button btSubmit, btshowAll;
    RequestQueue requestQueue;
    be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences prefrences=new be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences(this);
    String HTTP_JSON_URL="https://sitereck-1.000webhostapp.com/API/getMaterial.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material__request__cm);
        edmaterial=findViewById(R.id.etmaterial);
        edate=findViewById(R.id.etdate);
        btshowAll=findViewById(R.id.btn_show_request);
        btSubmit=findViewById(R.id.btsubmit);
        btSubmit.setOnClickListener(this);
        btshowAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show_request: {
                Intent i=new Intent(getApplicationContext(), MaterialRequestListCM.class);
                startActivity(i);
            }
            break;
            case R.id.btsubmit: {
//                Intent i=new Intent(getApplicationContext(),material_Request_CM.class);
//                startActivity(i);
                makeRequest();
            }
            break;
        }

    }

    private void makeRequest() {
        final String material = edmaterial.getText().toString().trim();
        final String date = edate.getText().toString().trim();
        final String projId=prefrences.getProjectId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,HTTP_JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        requestQueue.getCache().clear();
                        Toast.makeText(material_Request_CM.this,response,Toast.LENGTH_LONG).show();
                        parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(material_Request_CM.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("proj_id",projId);
                params.put("req_required_date",date);
                params.put("req_material",material);
                return params;
            }

        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void parseData(String response) {
    }
}
