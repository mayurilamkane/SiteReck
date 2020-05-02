package be.project.sitereck.GeneralActivities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.R;

public class  SignupActivity extends AppCompatActivity implements View.OnClickListener{
    //TODO: Complete Signup Remove NUll Pointer exception
    EditText name, email, password,contact,pass,rpass;
    RadioGroup radioGroup;
    RadioButton radiobutton;
    private Button button;
    ProgressDialog pd;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initData();
        button.setOnClickListener(this);
    }

    private void initData() {
        name  = findViewById(R.id.etname);
        email  = findViewById(R.id.etemail);
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
                    if( pass.getText().toString().equals(rpass.getText().toString()) ){

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

    private void RegisterUser(final String name, final String email, final String contact, final String password, final int position) {
        Toast.makeText(this, "position "+position, Toast.LENGTH_SHORT).show();
        pd = ProgressDialog.show(this,null,"Creating Your Profile");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallRegister(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("tagconvertstr", "["+response+"]");
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("user_registered").equals("1")){
                        if(pd!=null && pd.isShowing())
                            pd.dismiss();
                        Toast.makeText(SignupActivity.this, "Your Account Is Created ", Toast.LENGTH_SHORT).show();
                    }else if (object.getString("user_registered").equals("0")){
                        if(pd!=null && pd.isShowing())
                            pd.dismiss();
                        Toast.makeText(SignupActivity.this, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e.toString());
                    if(pd!=null && pd.isShowing())
                        pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                if(pd!=null && pd.isShowing())
                    pd.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",email.toLowerCase());
                params.put("password",password);
                params.put("contact",contact);
                params.put("position",String.valueOf(position));
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
