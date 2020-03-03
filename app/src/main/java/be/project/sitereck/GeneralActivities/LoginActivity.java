package be.project.sitereck.GeneralActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import be.project.sitereck.ProjectManager.Activities.ActivityManager_PM;
import be.project.sitereck.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    ProgressDialog progressDialog;
    Button btn_login;
    TextView tv_signup;
    EditText et_email,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initElements();
        initializeClickListener();
    }

    private void initializeClickListener() {
        btn_login.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
    }

    private void initElements() {
        btn_login = findViewById(R.id.btn_login);
        tv_signup = findViewById(R.id.tv_sign_up);
        et_password = findViewById(R.id.et_password);
        et_email= findViewById(R.id.et_email);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            btn_login_click();
        }

    }

    private void btn_login_click() {
//        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        if (et_email.getText().toString().equals("pm")){
            Intent intent =  new Intent(this, ActivityManager_PM.class);
            startActivity(intent);
        }

    }
}
