package be.project.sitereck.ProjectManager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import be.project.sitereck.R;

public class ProjectPMActivity extends AppCompatActivity implements View.OnClickListener{
    CardView cd_addProject,cd_ongoing_project , cd_constructor,cd_all_project,cd_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_pm);
        initElements();
        setClickListener();
    }

    private void setClickListener() {
        cd_update.setOnClickListener(this);
        cd_addProject.setOnClickListener(this);
        cd_ongoing_project.setOnClickListener(this);
        cd_constructor.setOnClickListener(this);
        cd_all_project.setOnClickListener(this);
    }

    private void initElements() {
        cd_addProject=(CardView)findViewById(R.id.cv_Project);
        cd_ongoing_project=(CardView)findViewById(R.id.cv_ongoing);
        cd_constructor=(CardView)findViewById(R.id.cv_constructor);
        cd_all_project=(CardView)findViewById(R.id.cv_all_Projects);
        cd_update = findViewById(R.id.cv_update);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cv_Project:
                startActivity(new Intent(ProjectPMActivity.this,AddNewProjectPM.class));
                break;
            case R.id.cv_ongoing:
                startActivity(new Intent(ProjectPMActivity.this,OngoingProjectPm.class));
                break;
            case R.id.cv_all_Projects:
                try {
                    startActivity(new Intent(ProjectPMActivity.this, AllProjectPm.class));
                }catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cv_update :
                try {
                    startActivity(new Intent(ProjectPMActivity.this, PmProjectList.class));
                }catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        
    }
}
