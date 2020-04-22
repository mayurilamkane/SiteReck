package be.project.sitereck.ProjectManager.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.Adapters.ActivitySearchAdapter;
import be.project.sitereck.ProjectManager.POJO.Activities;
import be.project.sitereck.R;

public class AddProjectActivityPM extends AppCompatActivity implements View.OnClickListener{

    AutoCompleteTextView tvactname;
    Button btsdate, btedate ;
    FloatingActionButton fabadd;
    List<Activities> searchlist = new ArrayList<>();
    ActivitySearchAdapter adapter;
    Activities activities;
    ArrayList<String> searchl = new ArrayList<String>();

    //
    String pid = null;
    String sdate = "",edate = "";
    ProgressDialog pd;
    RequestQueue rq ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project_activity_pm);
        initData();
        getActList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.custom_row, searchl);
//        adapter = new ActivitySearchAdapter(this , R.layout.custom_row , searchlist);
        tvactname.setThreshold(1);
        tvactname.setAdapter(adapter);

//
        btsdate.setOnClickListener(this);
        btedate.setOnClickListener(this);
        fabadd.setOnClickListener(this);
    }

    private void getActList() {
        //TODO: complete SearchList
        StringRequest  stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallAllactlist(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject object = new JSONObject(response);

                    if ( object.getString("status").equals("1") ) {
                        JSONArray jsonArray = object.getJSONArray("activity");
                        for (int i = 0 ; i < jsonArray.length() ; i++ ){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            Activities acti = new Activities(object1.getString("act_name").toString() , object1.getString("act_id").toString());
                            searchl.add(object1.getString("act_name"));
                            searchlist.add(acti);
                        }
                    }else if ( object.getString("status").equals("0") ) {

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
        });
        rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    private void initData() {
        tvactname = findViewById(R.id.searchviewaddact);
        btsdate = findViewById(R.id.btn_startDate);
        btedate = findViewById(R.id.btn_endDate);
        fabadd = findViewById(R.id.fab_actadd);

        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_actadd:
                AddActivityToPtoject();
                break;
            case R.id.btn_startDate:
                showCalender(btsdate);
                break;
            case R.id.btn_endDate:
                showCalender(btedate);
                break;

        }
    }

    private void AddActivityToPtoject() {
        if ( !tvactname.getText().toString().isEmpty() &&
                !sdate.isEmpty() && !edate.isEmpty()){
            String actName = tvactname.getText().toString();

            int id = getActId(actName);
            if (id != -1){
                //Add Activity method
                ADDACTIVITY(String.valueOf(id));

            }else{
                //TODO: call to create and insert method
                CreateAndAddActivity(actName);
            }

        }else {
            Toast.makeText(this, "Provide All Field", Toast.LENGTH_SHORT).show();
        }
    }

    private void CreateAndAddActivity(final String actName) {
        pd = ProgressDialog.show(AddProjectActivityPM.this,null,"Adding New Activity To Project");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallCnactproj(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("tagconvertstr", "["+response+"]");
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("activity_inserted").equals("1")){
                        if(pd!=null && pd.isShowing())
                            pd.dismiss();
                        Toast.makeText(AddProjectActivityPM.this, "Activity Addded ", Toast.LENGTH_SHORT).show();
                    }else if (object.getString("activity_inserted").equals("0")){
                        if(pd!=null && pd.isShowing())
                            pd.dismiss();
                        Toast.makeText(AddProjectActivityPM.this, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e.toString());
                    if(pd!=null && pd.isShowing())
                        pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                if(pd!=null && pd.isShowing())
                    pd.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String , String> params  = new HashMap<>();
                params.put("proj_id",pid);
                params.put("act_name",actName);
                params.put("start_date",sdate);
                params.put("end_date",edate);
//                params.put("status_date",sdate);
                return params;
            }
        };
        rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    private void ADDACTIVITY(final String act_id) {
        pd = ProgressDialog.show(AddProjectActivityPM.this,null,"Adding New Activity To Project");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallAddactproj(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("tagconvertstr", "["+response+"]");
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("activity_inserted").equals("1")){
                        if(pd!=null && pd.isShowing())
                            pd.dismiss();
                        Toast.makeText(AddProjectActivityPM.this, "Activity Addded ", Toast.LENGTH_SHORT).show();
                    }else if (object.getString("activity_inserted").equals("0")){
                        if(pd!=null && pd.isShowing())
                            pd.dismiss();
                        Toast.makeText(AddProjectActivityPM.this, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e.toString());
                    if(pd!=null && pd.isShowing())
                        pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                if(pd!=null && pd.isShowing())
                    pd.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String , String> params  = new HashMap<>();
                params.put("proj_id",pid);
                params.put("act_id",act_id);
                params.put("start_date",sdate);
                params.put("end_date",edate);
//                params.put("status_date",sdate);
                return params;
            }
        };
        rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    private int getActId(String actName) {
        int retId =-1;
        for (int i = 0 ; i < searchlist.size() ; i++){
            if (actName.toLowerCase().equals(searchlist.get(i).getName().toLowerCase())){
                retId = Integer.valueOf(searchlist.get(i).getId());
                break;
            }
        }
        System.out.println("Act_name = "+ actName.toLowerCase()+"  Act_id = "+retId);
        return retId;
    }

    private void showCalender(final Button btn) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View calenderView = inflater.inflate(R.layout.tab_calender,null);
        final DatePicker datePicker = calenderView.findViewById(R.id.date_pick);
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String TITLE="";
                if(btn.getId() == R.id.btn_startDate){
                    TITLE = "Start Date A:  ";
                    sdate = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
                }
                else {
                    TITLE = "Expected Date :  ";
                    edate = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
                }
                btn.setText(TITLE + datePicker.getDayOfMonth() + " / "+(datePicker.getMonth() + 1)+" / "+datePicker.getYear());


            }

        });

        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });
//                Pref.putString("event_date",event_date.getText().toString());
        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setView(calenderView
        );
        dialog.show();
    }
}
