package be.project.sitereck.ProjectManager.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.ProjectManager.Adapters.CustomAdapterForActivityDuration;
import be.project.sitereck.ProjectManager.POJO.DataActivityDuration;
import be.project.sitereck.R;

public class AddActivityDurationProjectPM extends AppCompatActivity {

    List<DataActivityDuration> list = new ArrayList<>();
    List<Integer> arrayList = new ArrayList<>();
    CustomAdapterForActivityDuration customAdapterForActivityDuration;
    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_duration_project_pm);
        recyclerView = findViewById(R.id.recycler_act_duration);
        Intent i = getIntent();
        arrayList = i.getIntegerArrayListExtra("SELECT_LIST_ID");
        for (int v:arrayList
        ) {
            list.add(new DataActivityDuration("ACT "+String.valueOf(v),String.valueOf(v)));
        }
        customAdapterForActivityDuration = new CustomAdapterForActivityDuration(AddActivityDurationProjectPM.this,list,AddActivityDurationProjectPM.this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(customAdapterForActivityDuration);

    }
}
