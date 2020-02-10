package be.project.sitereck.Construction_Manager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.Construction_Manager.Adapters.ProjectListAdapter;
import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralActivities.MainActivity;
import be.project.sitereck.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectList_cm extends AppCompatActivity implements ItemClickListener,View.OnClickListener{
    private RecyclerView recyclerView;
    List<ProjectDataClass> listItems;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list_cm);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        circleImageView=(CircleImageView)findViewById(R.id.img);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems=new ArrayList<>();

        circleImageView.setOnClickListener((View.OnClickListener)this);


        for (int i=0;i<=20;i++)
        {
            ProjectDataClass projectDataClass=new ProjectDataClass("Project" + (i+1),"Assigned By: ");
            listItems.add(projectDataClass);
        }
        ProjectListAdapter adapter = new ProjectListAdapter(listItems, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v, int adapterPosition) {

                listItems.get(adapterPosition);
                Intent intent = new Intent(ProjectList_cm.this, dashboard_cm.class);
                startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(ProjectList_cm.this,profile_cm.class));
    }
}
