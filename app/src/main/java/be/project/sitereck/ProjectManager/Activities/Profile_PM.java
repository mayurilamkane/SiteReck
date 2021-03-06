package be.project.sitereck.ProjectManager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import be.project.sitereck.GeneralActivities.LoginActivity;
import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.R;

public class Profile_PM extends AppCompatActivity {

    Button btnLogout;
    TextView name,  email, contact;
    AlertDialog.Builder alertDialog;
    SetSharedPrefrences prefrences = new SetSharedPrefrences(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pm);

        name = findViewById(R.id.pm_name);
        email = findViewById(R.id.pm_email);
        contact = findViewById(R.id.pm_contact);
        btnLogout = findViewById(R.id.btn_logout);

        name.setText(prefrences.getVar_User_name());
        email.setText(prefrences.getVar_User_email());
        contact.setText(prefrences.getVar_User_contact());

        alertDialog=new AlertDialog.Builder(this);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setMessage("Do you want to logout?").
                        setTitle("Log Out").
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                prefrences.clearPrefrences();
                                Toast.makeText(Profile_PM.this, "Logging Out !!!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.setTitle("Log Out");
                alert.show();
            }
        });

    }
}
