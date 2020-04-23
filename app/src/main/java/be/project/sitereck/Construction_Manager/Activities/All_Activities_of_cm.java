package be.project.sitereck.Construction_Manager.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import be.project.sitereck.Construction_Manager.Adapters.Activity_Adapter_cm;
import be.project.sitereck.Construction_Manager.DataClass.Activity_dataClass_cm;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.ProjectManager.POJO.ActivityManagerClass_PM;
import be.project.sitereck.R;

public class All_Activities_of_cm extends AppCompatActivity implements View.OnClickListener ,ItemClickListener {
    private RecyclerView recyclerView;
    private CheckBox checkbox;
    Activity_Adapter_cm adapter_cm;
    List<Activity_dataClass_cm> list;
    RequestQueue requestQueue;
    Button button;
    Activity_dataClass_cm activity_dataClass_cm;
    SetSharedPrefrences preferences1 = new SetSharedPrefrences( this );
    be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences prefrences = new be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences( this );

    ProgressDialog progressDialog;
    String HTTP_JSON_URL = "https://sitereck-1.000webhostapp.com/API/getActivityList.php";
    String HTTP_JSON_URL1 = "https://sitereck-1.000webhostapp.com/API/updateactivity.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_all__activities_of_cm );
        //Toast.makeText(this, "This Is On Going Project List", Toast.LENGTH_SHORT).show();
        list = new ArrayList<>();
        JSON_DATA_WEB_CALL();
        progressDialog = ProgressDialog.show( All_Activities_of_cm.this, "Please Wait", "Loading List", true, false );
        recyclerView = (RecyclerView) findViewById( R.id.recy_ptimeline );
        button = (Button) findViewById( R.id.checkbox_submit );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        adapter_cm = new Activity_Adapter_cm( this, (ArrayList<Activity_dataClass_cm>) list, (ItemClickListener) this );
        //recyclerView.setHasFixedSize(true);

        //recyclerView.setAdapter(adapter_cm);
        //button.setOnClickListener((View.OnClickListener)this);
        button.setOnClickListener( (View.OnClickListener) this );
    }

    private void JSON_DATA_WEB_CALL() {
        final String projId = prefrences.getProjectId();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, HTTP_JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );
                    JSONArray array = jsonObject.getJSONArray( "projects" );
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject( i );
                        Activity_dataClass_cm adc = new Activity_dataClass_cm( object.getString( "act_name" ), object.getString( "proj_status" ), object.getString( "p_act_start_date" ), object.getString( "p_act_end_date" ), object.getString( "p_act_id" ) );
                        list.add( adc );
                    }
                    recyclerView.setAdapter( adapter_cm );
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText( All_Activities_of_cm.this, "" + e.toString(), Toast.LENGTH_SHORT ).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( All_Activities_of_cm.this, "" + error.toString(), Toast.LENGTH_SHORT ).show();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "proj_id", projId );
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }


    private void JSON_DATA_update(final String id) {

//        final String p_act_id = prefrences.getP_act_id();
       // Toast.makeText( All_Activities_of_cm.this, id + " proj_activity_id foud", Toast.LENGTH_SHORT ).show();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, HTTP_JSON_URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i( "tagconvertstr", "[" + response + "]" );
                    JSONObject object = new JSONObject( response );
                    JSONArray jsonArray = object.getJSONArray( "completed" );
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object1 = jsonArray.getJSONObject( i );
                        Activity_dataClass_cm adc = new Activity_dataClass_cm( object.getString( "act_name" ), object.getString( "proj_status" ) );
                        list.add( adc );
                    }
                    recyclerView.setAdapter( adapter_cm );


                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println( e.toString() );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( All_Activities_of_cm.this, "" + error.toString(), Toast.LENGTH_SHORT ).show();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "p_act_id", id );
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v, final int adapterPosition) {
        list.get( adapterPosition );
        Activity_dataClass_cm activity_dataClass_cm=list.get(adapterPosition);
    }

    @Override
    public void onClick(View v) {
                if(adapter_cm.count>0) {
                    for (int i = 0; i < Activity_Adapter_cm.list.size(); i++) {
                        if ((Activity_Adapter_cm.list.get( i ).getSelected())) {

                            JSON_DATA_update( Activity_Adapter_cm.list.get( i ).getP_act_id() );

                            // Toast.makeText(All_Activities_of_cm.this, Activity_Adapter_cm.list.get( i ).getP_act_id()+ " proj_activity_id", Toast.LENGTH_SHORT).show();

                        }


                    }
                    Toast.makeText( All_Activities_of_cm.this, "project status Updated Successfully", Toast.LENGTH_LONG ).show();
                }
                else{
                    Toast.makeText( All_Activities_of_cm.this, "No Any Activity is selected", Toast.LENGTH_LONG ).show();
                }
            }


}
