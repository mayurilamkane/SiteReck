package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.project.sitereck.ProjectManager.POJO.ProjectData;
import be.project.sitereck.R;


public class ProjectDash extends AppCompatActivity implements View.OnClickListener{
    Button btnActivity,btnCM,btnReq,btreport;
    TextView title , address , sdate, edate, status ;
    Intent intent;
    ProjectData data;
    String proj_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_dash);

        data = (ProjectData) getIntent().getSerializableExtra("ProjectData");

        title = findViewById(R.id.tv_pname);
        address = findViewById(R.id.tv_paddr);
        sdate = findViewById(R.id.tv_psdate);
        edate = findViewById(R.id.tv_pedate);
        status = findViewById(R.id.tv_proj_status);

        title.setText(data.getProject_name());
        address.setText(data.getProject_Address());
        sdate.setText(data.getProject_Start_date());
        edate.setText(data.getProject_End_date());
        //status.setText(data.getProject_status());
        if(data.getProject_status().equals("0"))
        {
            status.setText("NOT STARTED");
        }
        else if(data.getProject_status().equals("1"))
        {
            status.setText("COMPLETED");
        }
        else
        {
            status.setText("ONGOING");
        }


        proj_id = data.getProject_id();

        btnActivity = findViewById(R.id.btAct);
        btnCM = findViewById(R.id.btCm);
        btnReq = findViewById(R.id.btReq);
        btreport = findViewById(R.id.btReport);

        btnActivity.setOnClickListener(this);
        btnCM.setOnClickListener(this);
        btnReq.setOnClickListener(this);
        btreport.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btAct:
                intent = new Intent(this,ActivityManager_PM.class);
                intent.putExtra("pid",data.getProject_id());
                startActivity(intent);
                break;

            case R.id.btCm:
                 intent = new Intent(this,ProjCMList.class);
                intent.putExtra("pid",data.getProject_id());
                startActivity(intent);
                break;

            case R.id.btReq:
                intent=new Intent(this,MaterialRequestFromCm.class);
                intent.putExtra("pid",data.getProject_id());
                startActivity(intent);
                break;
            case R.id.btReport:
                break;

        }

    }
}
