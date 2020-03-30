package be.project.sitereck.GeneralActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import be.project.sitereck.Construction_Manager.Activities.ProjectList_cm;
import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.ProjectManager.Activities.DashboardPMActivity;
import be.project.sitereck.R;

public class MainActivity extends AppCompatActivity {

    SetSharedPrefrences prefrences = new SetSharedPrefrences(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 9000);
        findUser();
    }

    private void findUser() {
        final  int isLogin = prefrences.getVar_login();
        if (isLogin == -1){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            int userPosition = prefrences.getVar_User_position();
            if (prefrences.getVar_User_position() ==  -1) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
//                Toast.makeText(this, "Something weird happened TRY AGAIN", Toast.LENGTH_SHORT).show();
            }else if (prefrences.getVar_User_position() ==  1) {
                System.out.println("inside pm ");
                Toast.makeText(this, "Logging IN PM", Toast.LENGTH_SHORT).show();
                //IF PM SEND TO DashBoardPMActivity.java
                Intent intent = new Intent(MainActivity.this, DashboardPMActivity.class);
                startActivity(intent);
                finish();

            }else if (prefrences.getVar_User_position() ==  2) {
                Toast.makeText(this, "Logging in CM", Toast.LENGTH_SHORT).show();
                //IF CM SEND TO ProjectList_cm.java
                Intent intent = new Intent(MainActivity.this, ProjectList_cm.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
