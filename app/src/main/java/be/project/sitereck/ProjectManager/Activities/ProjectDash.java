package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import be.project.sitereck.R;

public class ProjectDash extends AppCompatActivity implements View.OnClickListener{
    Button btnActivity,btnCM,btnReq;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_dash);

        btnActivity = findViewById(R.id.btAct);
        btnCM = findViewById(R.id.btCm);
        btnReq = findViewById(R.id.btReq);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btAct:
                intent = new Intent(this,ActivityManager_PM.class);
                startActivity(intent);
                break;

            case R.id.btCm:
                 intent = new Intent(this,ProjCMList.class);
                startActivity(intent);
                break;

            case R.id.btReq:
                break;

        }

    }
}
