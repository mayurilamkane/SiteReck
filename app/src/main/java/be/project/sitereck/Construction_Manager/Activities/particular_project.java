package be.project.sitereck.Construction_Manager.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;
import be.project.sitereck.Construction_Manager.SharedPref.SetSharedPrefrences;
import be.project.sitereck.R;


public class particular_project extends AppCompatActivity implements View.OnClickListener{
    TextView title, start_date, end_date, completed_work, ongoing_work, blocked_work;
    Button btn_location;
    private String JSON_URL="site";
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_project);

        title = (TextView) findViewById(R.id.text_title);
        start_date = (TextView) findViewById(R.id.text_startdate);
        end_date = (TextView) findViewById(R.id.text_enddate);
        btn_location = (Button) findViewById(R.id.btn_location);
        completed_work = (TextView) findViewById(R.id.complete_text);
        ongoing_work = (TextView) findViewById(R.id.ongoing_text);
        blocked_work = (TextView) findViewById(R.id.blocked_text);
        btn_location.setOnClickListener((View.OnClickListener) this);
      try {
          ProjectDataClass projectDataClass = SetSharedPrefrences.getInstance(this).getprojectinfo();
          title.setText(String.valueOf(projectDataClass.getTitle()));
          start_date.setText(String.valueOf(projectDataClass.getStartDate()));
          end_date.setText(String.valueOf(projectDataClass.getEndDate()));
      }catch (Exception e)
      {
          e.printStackTrace();
      }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_location:
                Intent intent = new Intent(particular_project.this, profile_cm.class);
                startActivity(intent);
        }
    }

    public void AsyncResultListener(int responseCode, String result) {
        int TOTAL_ONGOING =0;
        int TOTAL_BLOCKED =0;
        int TOTAL_COMPLETED =0;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int found = 0;
        try {
            found = jsonObject.getInt("found");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (found == 1){
            JSONArray projects = null;
            try {
                projects = jsonObject.getJSONArray("projects");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0 ; i< projects.length() ; i++){
                try {
                    JSONObject project = projects.getJSONObject(i);
                    if (project.getInt("proj_status") == 1){
                        TOTAL_ONGOING++;
                    }else if (project.getInt("proj_status") == 2){
                        TOTAL_COMPLETED++;
                    }else if (project.getInt("proj_status") == 3){
                        TOTAL_BLOCKED++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                list.add(new DataForCardProject(project.getString("proj_name"),project.getString("proj_address"),"",project.getString("proj_start_date"),project.getString("proj_end_date")));

            }
        }else{
//            list.add(new DataForCardProject("NO PROJECTS YET CREATED","","","",""));

        }
        completed_work.setText(String.valueOf(TOTAL_COMPLETED));
        ongoing_work.setText(String.valueOf(TOTAL_ONGOING));
        blocked_work.setText(String.valueOf(TOTAL_BLOCKED));


    }
}
