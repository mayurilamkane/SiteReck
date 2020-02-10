package be.project.sitereck.Construction_Manager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import be.project.sitereck.GeneralActivities.MainActivity;
import be.project.sitereck.R;

public class dashboard_cm extends AppCompatActivity implements View.OnClickListener {
    CardView cd_project,cd_activity,cd_report,cd_request,cd_CM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_cm);
        cd_project = (CardView)findViewById(R.id.cd_project);
        cd_activity= (CardView)findViewById(R.id.cd_activity);
        cd_report= (CardView)findViewById(R.id.cd_report);
        cd_request= (CardView)findViewById(R.id.cd_request);

        cd_project.setOnClickListener(this);
        cd_activity.setOnClickListener(this);
        cd_report.setOnClickListener(this);
        cd_request.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cd_activity:
                startActivity(new Intent(dashboard_cm.this, All_Activities_of_cm.class));
                break;
            case R.id.cd_report:
                startActivity(new Intent(dashboard_cm.this, MainActivity.class));
                break;
            case R.id.cd_request:
                startActivity(new Intent(dashboard_cm.this, material_Request_CM.class));
                break;
            case R.id.cd_project:
                startActivity(new Intent(dashboard_cm.this, particular_project.class));
                break;

        }
    }


}
