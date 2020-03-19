package be.project.sitereck.GeneralActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import be.project.sitereck.Construction_Manager.Activities.ProjectList_cm;
import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.Activities.DashboardPMActivity;
import be.project.sitereck.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    ProgressDialog progressDialog;
    Button btn_login;
    TextView tv_signup;
    EditText et_email,et_password;
    private RequestQueue rQueue;
    SetSharedPrefrences prefrences = new SetSharedPrefrences(this);

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
       if (!(et_email.getText().toString().isEmpty()) && !(et_password.getText().toString().isEmpty())){
           final String email = et_email.getText().toString().trim();
           final String pass = et_password.getText().toString().trim();

           StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallLogin(), new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
//                   rQueue.getCache().clear();
                   Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                   parseData(response);

               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   System.out.println(error.toString());
                   Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
               }
           }){
               @Override
               protected Map<String, String> getParams() {
                   Map<String,String> params = new HashMap<>();
                   params.put("email",email);
                   params.put("password",pass);

                   return params;
               }
           };
           rQueue = Volley.newRequestQueue(LoginActivity.this);
           rQueue.add(stringRequest);
       }

    }

    private void parseData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("user_found").equals("1")){
                prefrences.clearPrefrences();
                //TODO: create preferences

                //login to respective user
                Toast.makeText(this, jsonObject.getString("user_name"), Toast.LENGTH_SHORT).show();
                User user = new User(jsonObject.getInt("user_id"),jsonObject.getInt("user_position"),jsonObject.getString("user_name"),jsonObject.getString("user_email"),jsonObject.getString("user_contact"));

                prefrences.setLogin(1);
                prefrences.setUser_id(user.getId());
                prefrences.setUser_email(user.getEmail());
                prefrences.setUser_name(user.getName());
                prefrences.setUser_contact(user.getContact());
                prefrences.setUser_position(user.getPosi());

                System.out.println(prefrences.getSharedUserId()+" "+prefrences.getVar_User_position()+" "+prefrences.getVar_login());

                if (prefrences.getVar_User_position() ==  -1) {
                    Toast.makeText(this, "Something weird happened TRY AGAIN", Toast.LENGTH_SHORT).show();
                }else if (prefrences.getVar_User_position() ==  1) {
                    System.out.println("inside pm ");
                    Toast.makeText(this, "Logging IN PM", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, DashboardPMActivity.class);
//                    finish();
                    startActivity(intent);

                }else if (prefrences.getVar_User_position() ==  2) {
                    Toast.makeText(this, "Logging in CM", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, ProjectList_cm.class);
//                    finish();
                    startActivity(intent);
                }
                }

            } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
