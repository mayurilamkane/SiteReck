package be.project.sitereck.Construction_Manager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.Construction_Manager.Adapters.SubActivityAdapter_CM;
import be.project.sitereck.Construction_Manager.DataClass.SubActivityClass_CM;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralActivities.MainActivity;
import be.project.sitereck.R;

public class ProjectSubActivities_CM extends AppCompatActivity implements View.OnClickListener, ItemClickListener {

    RecyclerView recyclerView;
    List<SubActivityClass_CM> data;
    SubActivityAdapter_CM adapter;
    Button bt_upl,bt_plan,bt_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_sub_activities__cm);

        try {
            bt_plan=findViewById(R.id.bt_plan);
            bt_plan.setOnClickListener(this);
            bt_submit=findViewById(R.id.bt_submit);
            bt_submit.setOnClickListener(this);
            data=new ArrayList<>();
            initData();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter=new SubActivityAdapter_CM(data,this);
            recyclerView.setAdapter(adapter);

        }
        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        recyclerView=findViewById(R.id.recy_subAct);
        data.add(new SubActivityClass_CM("Sub Activity 1"));
        data.add(new SubActivityClass_CM("Sub Activity 2"));
        data.add(new SubActivityClass_CM("Sub Activity 3"));
        data.add(new SubActivityClass_CM("Sub Activity 4"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_plan:{
                startActivity(new Intent(ProjectSubActivities_CM.this, MainActivity.class));
            } break;
            case R.id.bt_submit:{
                Toast.makeText(getApplicationContext(),"SubActivity is submitted", Toast.LENGTH_SHORT).show();
            } break;
        }

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v, int adapterPosition) {
        data.get(adapterPosition);
    }
}
