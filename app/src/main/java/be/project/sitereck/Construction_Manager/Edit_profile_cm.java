package be.project.sitereck.Construction_Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import be.project.sitereck.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class Edit_profile_cm extends AppCompatActivity implements View.OnClickListener {

    EditText editText1,editText2,editText3;

    Button btn_save;
    CircleImageView btn_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_cm);
        editText1 =(EditText)findViewById(R.id.edit_text1);
        editText2 =(EditText)findViewById(R.id.edit_text2);
        editText3 =(EditText)findViewById(R.id.edit_text3);
        btn_edit =(CircleImageView) findViewById(R.id.edit_profile);
        btn_save =(Button) findViewById(R.id.save_btn);
        btn_edit.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
      String name;
        switch (v.getId()) {
            case R.id.save_btn:
                Intent intent = new Intent(Edit_profile_cm.this, update_activity.class);
                startActivity(intent);
              name=editText1.getText().toString();


        }
    }
}
