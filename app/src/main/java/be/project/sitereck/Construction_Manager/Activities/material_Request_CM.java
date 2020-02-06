package be.project.sitereck.Construction_Manager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import be.project.sitereck.Construction_Manager.DataClass.RequestitemClass_CM;
import be.project.sitereck.R;

public class material_Request_CM extends AppCompatActivity implements View.OnClickListener {

    public EditText edmaterial, edate;
    Button btSubmit, btshowAll;
    ArrayList<RequestitemClass_CM> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material__request__cm);
        edmaterial=findViewById(R.id.etmaterial);
        edate=findViewById(R.id.etdate);
        btshowAll=findViewById(R.id.btn_show_request);
        btSubmit=findViewById(R.id.btsubmit);
        btSubmit.setOnClickListener(this);
        btshowAll.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show_request: {
                Intent i=new Intent(getApplicationContext(),MaterialRequestListCM.class);
                startActivity(i);
            }
            break;

        }

    }
}
