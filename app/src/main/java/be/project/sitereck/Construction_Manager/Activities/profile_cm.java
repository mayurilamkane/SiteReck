package be.project.sitereck.Construction_Manager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;
import be.project.sitereck.GeneralActivities.LoginActivity;
import be.project.sitereck.GeneralActivities.User;
import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.R;

import static android.view.View.OnClickListener;

public class profile_cm extends AppCompatActivity  {

    ImageView image;
    Button btn_edit;
    TextView name,email,contact,Name1;
private  static  final  int PICK_IMAGE=1;
    SetSharedPrefrences prefrences = new SetSharedPrefrences(this);
Uri imageUri;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_cm);
        image=(ImageView)findViewById(R.id.profile_image);
        //btn_edit=( Button)findViewById(R.id.edit_btn);
        name=(TextView) findViewById( R.id.name );
        email=(TextView) findViewById( R.id.email );
        contact=(TextView) findViewById( R.id.contact );
        Name1=(TextView) findViewById( R.id.profile_name );
        ImageView imgLogoutcm;
        imgLogoutcm = findViewById(R.id.imglogoutcm);
        alertDialog=new AlertDialog.Builder(this);
        imgLogoutcm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGOUTCM();
            }
        });
       // btn_edit.setOnClickListener((View.OnClickListener) this);
        try {
            User user = SetSharedPrefrences.getInstance(this).getprojectinfo();
            name.setText(String.valueOf(user.getName()));
            email.setText(String.valueOf(user.getEmail()));
            contact.setText(String.valueOf(user.getContact()));
            Name1.setText( String.valueOf(user.getName()));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void LOGOUTCM() {
        alertDialog.setMessage("Do you want to logout?").
                setTitle("Log Out").
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prefrences.clearPrefrences();
                        Toast.makeText(profile_cm.this, "Logging Out !!!", Toast.LENGTH_SHORT).show();
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

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_btn:
                Intent intent = new Intent(profile_cm.this, Edit_profile_cm.class);
                startActivity(intent);
        }
    }*/
}

