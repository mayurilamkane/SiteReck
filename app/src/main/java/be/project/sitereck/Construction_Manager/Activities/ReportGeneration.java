package be.project.sitereck.Construction_Manager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import be.project.sitereck.R;

public class ReportGeneration extends AppCompatActivity {

    String HTTP_JSON_URL = "https://sitereck-1.000webhostapp.com/API/ReportGenerator/GenerateReport1.php";
    Button btn;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_generation);
        btn=(Button)findViewById(R.id.button);
        alertDialog=new AlertDialog.Builder(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setMessage("Do you want to download this report? ").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String uri = Uri.parse(HTTP_JSON_URL)
                                .buildUpon()
                                .appendQueryParameter("proj_id", "2")
                                .build().toString();
                        i.setData(Uri.parse(uri));

                        startActivity(i);
                        Toast.makeText(ReportGeneration.this, "You choose yes action for alertbox", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(ReportGeneration.this, "You choose no action for alertbox", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.setTitle("Download");
                alert.show();
            }
        });

        }
    }

