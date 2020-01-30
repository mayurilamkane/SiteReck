package be.project.sitereck.Construction_Manager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.Construction_Manager.Adapters.Activity_Adapter_cm;
import be.project.sitereck.Construction_Manager.DataClass.Activity_dataClass_cm;
import be.project.sitereck.MainActivity;
import be.project.sitereck.R;

public class All_Activities_of_cm extends AppCompatActivity implements View.OnClickListener,ItemClickListener {
    List<Activity_dataClass_cm> list;
    RecyclerView recyclerView;
    Activity_Adapter_cm adapter_cm;
    String title,startdate[],endDate[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__activities_of_cm);
        Toast.makeText(this, "This Is On Going Project List", Toast.LENGTH_SHORT).show();
        list = new ArrayList<>();

        initData();
        adapter_cm = new Activity_Adapter_cm(this,list,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter_cm);


    }

    private void initData() {
        recyclerView=(RecyclerView)findViewById(R.id.recy_ptimeline);
        list.add(new Activity_dataClass_cm("title 1","status","1/1/1","1/1/1"));
        list.add(new Activity_dataClass_cm("title 2","status","1/1/1","1/1/1"));
        list.add(new Activity_dataClass_cm("title 3","status","1/1/1","1/1/1"));
        list.add(new Activity_dataClass_cm("title 4","status","1/1/1","1/1/1"));
        list.add(new Activity_dataClass_cm("title 5","status","1/1/1","1/1/1"));

    }

    @Override
    public void onClick(View v,int position) {
        list.get(position);
Intent intent =new Intent(All_Activities_of_cm.this, MainActivity.class);
        startActivity(intent);

        Toast.makeText(this, list.get(position).getTitle() +"  " + list.get(position).getId(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {

    }
}
