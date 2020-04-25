package be.project.sitereck.ProjectManager.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.POJO.DataAddProject;
import be.project.sitereck.ProjectManager.POJO.NewProjectDetails;
import be.project.sitereck.R;

public class AddNewProjectPM extends AppCompatActivity implements View.OnClickListener {
    Button btn_startDate, btn_endDate, btn_map,btn_save;
    EditText edt_proj_name, edt_proj_des;
    public static NewProjectDetails list;
    HelperClass helperClass;
    public static DataAddProject dataAddProject = new DataAddProject();

    String sdate = "",edate = "",lat = "",longe = "";
    SetSharedPrefrences preferences = new SetSharedPrefrences(this);

    ProgressDialog pd;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_project_pm);
        ActivityCompat.requestPermissions(AddNewProjectPM.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.SEND_SMS},100);

        initElements();
        initClickListeners();

    }

    private void initClickListeners() {
        helperClass = new HelperClass();
        btn_startDate.setOnClickListener(this);
        btn_endDate.setOnClickListener(this);
        btn_map.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    private void initElements() {
        btn_startDate = findViewById(R.id.btn_startDate);
        btn_endDate = findViewById(R.id.btn_endDate);
        btn_save = findViewById(R.id.btn_save);
        edt_proj_name = findViewById(R.id.edt_proj_name);
        edt_proj_des = findViewById(R.id.edt_proj_desc);
        btn_map = findViewById(R.id.btn_map);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_startDate:
                showCalender(btn_startDate);
                break;
            case R.id.btn_endDate:
                showCalender(btn_endDate);
                break;

            case R.id.btn_map:
//                dataAddProject.setPROJECT_NAME(helperClass.getStringEditText(edt_proj_name));
//                dataAddProject.setPROJECT_DESC(helperClass.getStringEditText(edt_proj_des));
//                dataAddProject.setPROJECT_LATITUDE("0");
//                dataAddProject.setPROJECT_LONGITUDE("0");
//                startActivity(new Intent(AddNewProjectPM.this,AddProjectActivityPM.class));
                startActivityForResult(new Intent(AddNewProjectPM.this,MapActivity.class),2);
                break;
            case  R.id.btn_save:
//                Toast.makeText(this, "Call save API here", Toast.LENGTH_SHORT).show();
                AddNewProject();
                break;
        }

    }

    private void AddNewProject() {
        final String title,desc,uid;
        uid = preferences.getVar_User_id();
        if( !edt_proj_name.getText().toString().isEmpty() &&
                !edt_proj_des.getText().toString().isEmpty() &&
                !sdate.isEmpty() &&
                !edate.isEmpty() &&
                !lat.isEmpty()   &&
                !longe.isEmpty()
        ){
            pd = ProgressDialog.show(AddNewProjectPM.this,null,"Creating Your Profile");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallAddproj(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("tagconvertstr", "["+response+"]");
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("1")){
                            if(pd!=null && pd.isShowing())
                                pd.dismiss();
                            Toast.makeText(AddNewProjectPM.this, "Project Is Created ", Toast.LENGTH_SHORT).show();
                        }else if (object.getString("status").equals("0")){
                            if(pd!=null && pd.isShowing())
                                pd.dismiss();
                            Toast.makeText(AddNewProjectPM.this, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
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
                    Map<String ,String> params = new HashMap<>();
                    params.put("title",edt_proj_name.getText().toString());
                    params.put("desc",edt_proj_des.getText().toString());
                    params.put("sdate",sdate);
                    params.put("edate",edate);
                    params.put("lat",lat);
                    params.put("longe",longe);
                    params.put("uid",uid);
                    return params;
                }
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }else{
            Toast.makeText(this, "Please Provide All Fields", Toast.LENGTH_LONG).show();
        }

    }

    private void showCalender(final Button btn){
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
                    TITLE = "Start Date of Project:  ";
                    dataAddProject.setPROJECT_START_DATE((datePicker.getMonth() + 1)+" / "+datePicker.getYear());
                    sdate = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
                }
                else {
                    TITLE = "COMPLETION DATE OF PROJECT :  ";
                    dataAddProject.setPROJECT_END_DATE((datePicker.getMonth() + 1)+" / "+datePicker.getYear());
                }
                btn.setText(TITLE + datePicker.getDayOfMonth() + " / "+(datePicker.getMonth() + 1)+" / "+datePicker.getYear());
                edate = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            try {
                String Latitude = data.getStringExtra("Latitude");
                String Longitude = data.getStringExtra("Longitude");
//                Toast.makeText(this, Latitude, Toast.LENGTH_SHORT).show();
                dataAddProject.setPROJECT_NAME(helperClass.getStringEditText(edt_proj_name));
                dataAddProject.setPROJECT_DESC(helperClass.getStringEditText(edt_proj_des));
                dataAddProject.setPROJECT_LATITUDE(Latitude);
                dataAddProject.setPROJECT_LONGITUDE(Longitude);
                btn_save.setVisibility(View.VISIBLE);
                System.out.println("lat = "+dataAddProject.getPROJECT_LATITUDE());
                System.out.println("long = "+dataAddProject.getPROJECT_LONGITUDE());
                lat = Latitude; longe = Longitude;
//
            }catch (Exception e){
                Toast.makeText(this, "Please select the location to continue", Toast.LENGTH_SHORT).show();
                btn_save.setVisibility(View.INVISIBLE);
            }
        }
    }
}

