package be.project.sitereck.ProjectManager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import be.project.sitereck.R;

public class AddProjectActivityPM extends AppCompatActivity {

    LinearLayout cb_layout;
    final ArrayList<Integer> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project_pm);
        initElements();
        addCB();
    }

    private void addCB() {
        for (int i = 0 ;i<5;i++){
            final CheckBox cb = new CheckBox(this);
            cb.setText("CB "+String.valueOf(i));
            cb.setId(i);
            cb_layout.addView(cb);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        Log.d("CB_STATUS", "onCheckedChanged: "+"CHECKED  "+cb.getId());
                        arrayList.add(buttonView.getId());
//                        try {
//                            list.add(new DataAddingDuratioinForSubactivity(0,buttonView.getText().toString(),"",activity.getInt("activity_id")));
//                            adapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }else{
                        Log.d("CB_STATUS", "onCheckedChanged: "+"UNCHECKED  "+cb.getId());
                        int id = arrayList.indexOf(buttonView.getId());
                        arrayList.remove(id);
                    }
                }
            });
        }

    }

    private void initElements() {
        cb_layout  = findViewById(R.id.lyt_parent);
    }

    public void proceedNext(View view) {
        Intent intent = new Intent(AddProjectActivityPM.this,AddActivityDurationProjectPM.class);
        intent.putExtra("SELECT_LIST_ID",arrayList);
        startActivity(intent);
    }
}
