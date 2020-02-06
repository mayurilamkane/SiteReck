package be.project.sitereck.Construction_Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.List;

import be.project.sitereck.R;

public class ProjectList_CM_y extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ProjectDataClass> list; // Create list for data class
    String title,startdate[],endDate[];
    LinearLayout ongoing_lay,all_lay,profile_lay;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list__cm_yuvraj);

        initElements();
    }

    private void initElements() {
        //use this method to initialize every view element
    }
}
