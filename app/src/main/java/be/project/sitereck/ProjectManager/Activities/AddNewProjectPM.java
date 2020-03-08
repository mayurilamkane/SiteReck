package be.project.sitereck.ProjectManager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import be.project.sitereck.ProjectManager.POJO.DataAddProject;
import be.project.sitereck.ProjectManager.POJO.NewProjectDetails;
import be.project.sitereck.R;

public class AddNewProjectPM extends AppCompatActivity implements View.OnClickListener {
Button btn_startDate, btn_endDate, btn_map;
EditText edt_proj_name, edt_proj_des;
public static NewProjectDetails list;
HelperClass helperClass;
public static DataAddProject dataAddProject = new DataAddProject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_project_pm);
        initElements();
        initClickListeners();

    }

    private void initClickListeners() {
        helperClass = new HelperClass();
        btn_startDate.setOnClickListener(this);
        btn_endDate.setOnClickListener(this);
        btn_map.setOnClickListener(this);
    }

    private void initElements() {
        btn_startDate = findViewById(R.id.btn_startDate);
        btn_endDate = findViewById(R.id.btn_endDate);
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
                dataAddProject.setPROJECT_NAME(helperClass.getStringEditText(edt_proj_name));
                dataAddProject.setPROJECT_DESC(helperClass.getStringEditText(edt_proj_des));
                dataAddProject.setPROJECT_LATITUDE("0");
                dataAddProject.setPROJECT_LONGITUDE("0");
                startActivity(new Intent(AddNewProjectPM.this,AddProjectActivityPM.class));
                break;

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
                }
                    else {
                TITLE = "COMPLETION DATE OF PROJECT :  ";
                dataAddProject.setPROJECT_END_DATE((datePicker.getMonth() + 1)+" / "+datePicker.getYear());
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
