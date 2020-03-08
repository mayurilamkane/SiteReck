package be.project.sitereck.ProjectManager.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.roger.match.library.MatchTextView;

import be.project.sitereck.R;

public class DashboardPMActivity extends AppCompatActivity implements View.OnClickListener {
CardView cd_project, cd_activity, cd_request, cd_progress,cd_CM;
MatchTextView heading;
TextView tv_ongoing, tv_completed, tv_blocked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_pm);
        initElements();

        setOnClickListener();
    }

    private void setOnClickListener() {
        cd_project.setOnClickListener(this);
        cd_activity.setOnClickListener(this);
        cd_progress.setOnClickListener(this);
        cd_request.setOnClickListener(this);
        cd_CM.setOnClickListener(this);
    }

    private void initElements() {
        cd_project = (CardView)findViewById(R.id.cd_project);
        cd_activity = (CardView)findViewById(R.id.cd_activity);
        cd_request = (CardView)findViewById(R.id.cd_request);
        cd_progress = (CardView)findViewById(R.id.cd_progress);
        cd_CM = (CardView)findViewById(R.id.cd_manager);

        tv_ongoing = (TextView)findViewById(R.id.tv_ongoing);
        tv_blocked= (TextView)findViewById(R.id.tv_blocked);
        tv_completed = (TextView)findViewById(R.id.tv_completed);

        heading = (MatchTextView)findViewById(R.id.heading_sitereck);
        setHeader();
    }

    private void setHeader() {
        heading.setText("SITERECK");
        heading.setTextSize(40);
        heading.setTextColor(Color.WHITE);
        heading.setProgress(0.5f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cd_project:
                startActivity(new Intent(DashboardPMActivity.this,ProjectPMActivity.class));
                break;
            case R.id.cd_activity:
                startActivity(new Intent(DashboardPMActivity.this,ActivityPm.class));
                break;
        }

    }
}
