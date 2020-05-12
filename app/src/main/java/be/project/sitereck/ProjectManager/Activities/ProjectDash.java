package be.project.sitereck.ProjectManager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.Better.Activities.MaterialRequest.PM_AllMatReq;
import be.project.sitereck.ProjectManager.POJO.ProjectData;
import be.project.sitereck.ProjectManager.POJO.ProjectMiscData;
import be.project.sitereck.R;


public class ProjectDash extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    ImageView burgerimg;
    TextView toptitle;

    Button btnActivity,btnCM,btnReq,btreport, btnMap;
    TextView title , address , sdate, edate, status , edit, desc, token;
    Intent intent;
    ProjectData data;
    String proj_id;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_dash);

        toolbar = findViewById(R.id.toolbar);
        toptitle = findViewById(R.id.title_top);
        toptitle.setText("Project Dashboard");
        burgerimg = findViewById(R.id.menu_icon);
        burgerimg.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

        data = (ProjectData) getIntent().getSerializableExtra("ProjectData");

        title = findViewById(R.id.tv_pname);
        desc = findViewById(R.id.tv_pdesc);
        address = findViewById(R.id.tv_paddr);
        sdate = findViewById(R.id.tv_psdate);
        edate = findViewById(R.id.tv_pedate);
        status = findViewById(R.id.tv_proj_status);
        edit = findViewById(R.id.tv_projeditinfo);
        token = findViewById(R.id.tv_projtoken);

//        title.setText(data.getProject_name());
//        address.setText(data.getProject_Address());
//        sdate.setText(data.getProject_Start_date());
//        edate.setText(data.getProject_End_date());
//        //status.setText(data.getProject_status());
//        if(data.getProject_status().equals("0"))
//        {
//            status.setText("NOT STARTED");
//        }
//        else if(data.getProject_status().equals("1"))
//        {
//            status.setText("COMPLETED");
//        }
//        else
//        {
//            status.setText("ONGOING");
//        }

        title.setText(ProjectMiscData.getProject_name());
        desc.setText(ProjectMiscData.getProject_description());
        address.setText(ProjectMiscData.getProject_Address());
        sdate.setText(ProjectMiscData.getProject_Start_date());
        edate.setText(ProjectMiscData.getProject_End_date());
        token.setText(ProjectMiscData.getProj_token());
        //status.setText(data.getProject_status());
        if(data.getProject_status().equals("0"))
        {
            status.setText("NOT STARTED");
        }
        else if(data.getProject_status().equals("1"))
        {
            status.setText("COMPLETED");
        }
        else
        {
            status.setText("ONGOING");
        }


        proj_id = data.getProject_id();

        btnActivity = findViewById(R.id.btAct);
        btnCM = findViewById(R.id.btCm);
        btnReq = findViewById(R.id.btReq);
        btreport = findViewById(R.id.btReport);
        btnMap = findViewById(R.id.btn_map);

        btnActivity.setOnClickListener(this);
        btnCM.setOnClickListener(this);
        btnReq.setOnClickListener(this);
        btreport.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        edit.setOnClickListener(this);

        burgerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btAct:
                intent = new Intent(this,ActivityManager_PM.class);
                intent.putExtra("pid",data.getProject_id());
                startActivity(intent);
                break;

            case R.id.btCm:
                intent = new Intent(this,ProjCMList.class);
                intent.putExtra("pid",data.getProject_id());
                startActivity(intent);
                break;

            case R.id.btReq:
                intent=new Intent(this, PM_AllMatReq.class);
                intent.putExtra("pid",data.getProject_id());
                startActivity(intent);
                break;
            case R.id.btReport:
                DownloadReport();
                break;
            case R.id.btn_map:
            {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Float.parseFloat(data.getProject_latitude()),Float.parseFloat(data.getProject_longitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
            break;
            case R.id.tv_projeditinfo:
            {
                Intent intent = new Intent(ProjectDash.this, Edit_ProjectInfo.class);
                startActivity(intent);
            }
            break;
        }

    }

    private void DownloadReport() {
        alertDialog=new AlertDialog.Builder(this);
        alertDialog.setMessage("Do you want to download this report? ").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent i = new Intent(Intent.ACTION_VIEW);
                String uri = Uri.parse(URL_STRINGS.getCallDwnldreport())
                        .buildUpon()
                        .appendQueryParameter("proj_id", data.getProject_id())
                        .build().toString();
                i.setData(Uri.parse(uri));

                startActivity(i);
                Toast.makeText(ProjectDash.this, "You choose yes action for alertbox", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(ProjectDash.this, "You choose no action for alertbox", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.setTitle("Download");
        alert.show();
    }
}
