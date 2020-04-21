package be.project.sitereck.Construction_Manager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import be.project.sitereck.GeneralActivities.MainActivity;
import be.project.sitereck.R;

public class dashboard_cm extends AppCompatActivity implements View.OnClickListener {
CardView cv_project,cv_request,cv_activity,cv_report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_cm);

        cv_project=(CardView)findViewById(R.id.cd_project);
        cv_request=(CardView)findViewById(R.id.cd_request);
        cv_activity=(CardView)findViewById(R.id.cd_activity);
        cv_report=(CardView)findViewById(R.id.cd_report);

        cv_project.setOnClickListener(this);
        cv_request.setOnClickListener(this);
        cv_activity.setOnClickListener(this);
        cv_report.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cd_project:
                startActivity(new Intent(dashboard_cm.this,particular_project.class));
                break;
            case R.id.cd_request:
                startActivity(new Intent(dashboard_cm.this,material_Request_CM.class));
                break;
            case R.id.cd_activity:
                startActivity(new Intent(dashboard_cm.this,All_Activities_of_cm.class));
                break;
            case R.id.cd_report:
                startActivity(new Intent(dashboard_cm.this,ReportGeneration.class));
                break;
        }
    }
}



