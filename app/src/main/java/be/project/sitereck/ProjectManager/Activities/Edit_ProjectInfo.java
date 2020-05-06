package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import be.project.sitereck.R;

public class Edit_ProjectInfo extends AppCompatActivity {

    EditText et_pname , et_pdesc;
    TextView tv_sdate, tv_edate , tv_address;
    Button btn_chloc;
    FloatingActionButton fab_done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprojectinfo);
        InitView();
        setTextData();
    }

    private void setTextData() {

    }

    private void InitView() {
        et_pname = findViewById(R.id.edt_proj_name);
        et_pdesc = findViewById(R.id.edt_proj_desc);
        tv_sdate = findViewById(R.id.tve_sdate);
        tv_edate = findViewById(R.id.tve_edate);
        tv_address = findViewById(R.id.tve_address);
        btn_chloc = findViewById(R.id.btchlocation);
        fab_done = findViewById(R.id.fab_editproject);
    }
}
