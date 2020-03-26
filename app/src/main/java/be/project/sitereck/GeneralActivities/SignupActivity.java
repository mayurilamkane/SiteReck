package be.project.sitereck.GeneralActivities;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;

import be.project.sitereck.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
//TODO: Complete Signup Remove NUll Pointer exception
    private EditText name, email, password,contact,pass,rpass;
    private RadioGroup radioGroup;
    private RadioButton radiobutton;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initData();
        button.setOnClickListener(this);
    }

    private void initData() {
        name  = findViewById(R.id.etname);
        email  = findViewById(R.id.et_email);
        contact  = findViewById(R.id.etcont);
        pass  = findViewById(R.id.etpass);
        rpass  = findViewById(R.id.etrpass);
        radioGroup  = findViewById(R.id.radio);
        button  = findViewById(R.id.btn_signup);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_signup){
            if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !contact.getText().toString().isEmpty() && !pass.getText().toString().isEmpty() && !rpass.getText().toString().isEmpty() && (radioGroup.getCheckedRadioButtonId() != -1)){
                if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    if(pass.getText().toString() == rpass.getText().toString()){

                        int selectpos = radioGroup.getCheckedRadioButtonId();
                        int position;
                        radiobutton =  findViewById(selectpos);

                        if(radiobutton.getText().toString().trim().equals("Project Manager")){
                            position = 1;
                        }else {position  = 2;}
                        RegisterUser(name.getText().toString(), email.getText().toString(), contact.getText().toString(), pass.getText().toString(), position);

                    }else {
                        Toast.makeText(this, "Enter Same Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Enter All The Field", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void RegisterUser(String name, String email, String contact, String password, int position) {
        Toast.makeText(this, "position "+position, Toast.LENGTH_SHORT).show();
//        StringRequest stringRequest
    }
}
