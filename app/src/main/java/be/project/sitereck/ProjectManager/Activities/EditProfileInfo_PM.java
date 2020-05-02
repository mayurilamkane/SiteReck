package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
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

import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.R;

public class EditProfileInfo_PM extends AppCompatActivity {

    EditText name , email , contact;
    Button btn_edit;
    SetSharedPrefrences prefrences = new SetSharedPrefrences(this);
    RequestQueue rq ;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofileinfo_pm);

        name = findViewById(R.id.etename);
        email = findViewById(R.id.eteemail);
        contact = findViewById(R.id.etecont);
        btn_edit = findViewById(R.id.btn_update);

        name.setText(prefrences.getVar_User_name());
        email.setText(prefrences.getVar_User_email());
        contact.setText(prefrences.getVar_User_contact());

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateInfo();
            }
        });
    }

    private void UpdateInfo() {
        if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !contact.getText().toString().isEmpty()){
            if (name.getText().toString().equals(prefrences.getVar_User_name()) &&
                    email.getText().toString().equals(prefrences.getVar_User_email()) &&
                    contact.getText().toString().equals(prefrences.getVar_User_contact())   ){

                Toast.makeText(this, "No Changes Made", Toast.LENGTH_SHORT).show();

            }else{
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallEditinfo(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("info_updated").equals("1")){
                                prefrences.setUser_email(jsonObject.getString("email"));
                                prefrences.setUser_name(jsonObject.getString("name"));
                                prefrences.setUser_contact(jsonObject.getString("contact"));
                            }else {
                                Toast.makeText(EditProfileInfo_PM.this, "Uh Oh!!! Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map <String , String > params = new HashMap<>();
                        params.put("name", name.getText().toString());
                        params.put("email", email.getText().toString().toLowerCase());
                        params.put("contact", contact.getText().toString());
                        params.put("uid",prefrences.getVar_User_id());
                        System.out.println("params -> "+params);
                        return params;
                    }
                };
                rq = Volley.newRequestQueue(this);
                rq.add(stringRequest);
            }

        }else{
            Toast.makeText(this, "Enter All Information", Toast.LENGTH_SHORT).show();
        }
    }
}
