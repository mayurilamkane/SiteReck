package be.project.sitereck.ProjectManager.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.GeneralClasses.ConnectionClass;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.GeneralInterfaces.AsyncResult;
import be.project.sitereck.ProjectManager.Adapters.CustomAdapterForCardProject;
import be.project.sitereck.ProjectManager.POJO.DataForCardProject;
import be.project.sitereck.R;

public class OngoingProjectPm extends AppCompatActivity implements AsyncResult {
    RecyclerView recyclerView;
    static List<DataForCardProject> list;
    static CustomAdapterForCardProject adapter;
//    String NAME[];
//    String ADDRESS[];
//    String CONSTRUCT[];
//    String ASSIGN[];
//    String EXPECT[];

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ongoing_project_pm);
        initElements();
        progressDialog = ProgressDialog.show(OngoingProjectPm.this,"Please Wait","MAKING YOUR LIST READY",true,false);

//        progressDialog = new ProgressDialog();
//        NAME= getResources().getStringArray(R.array.pname);
//        ADDRESS= getResources().getStringArray(R.array.paddress);
//        CONSTRUCT= getResources().getStringArray(R.array.pconengg);
//        ASSIGN= getResources().getStringArray(R.array.passigdt);
//        EXPECT= getResources().getStringArray(R.array.pexpdt);


        try {
            list = new ArrayList<>();
//            String post_data = URLEncoder.encode("u_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(new SetSharedPrefrences(this).getSharedUserId()), "UTF-8");
//         Toast.makeText(this, post_data, Toast.LENGTH_SHORT).show();
            String post_data = URLEncoder.encode("u_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(1), "UTF-8");

            new ConnectionClass(this,1).execute(new URL_STRINGS().getONGOINGPROJECTS(),post_data);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



    private void initElements() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_project);


    }

    private void setData(String result) {
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        Log.d("RESULTS", "setData: "+result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            int found = jsonObject.getInt("found");
            if (found == 1){
                JSONArray projects = jsonObject.getJSONArray("projects");
                for (int i = 0 ; i< projects.length() ; i++){
                    JSONObject project = projects.getJSONObject(i);
                    list.add(new DataForCardProject(project.getString("proj_name"),project.getString("proj_address"),"",project.getString("proj_start_date"),project.getString("proj_end_date")));

                }
            }else{
                list.add(new DataForCardProject("NO PROJECTS YET CREATED","","","",""));

            }
        } catch (JSONException e) {
            //e.printStackTrace();
            Log.d("Exception in ", "setData: "+e.toString());
        }

    }

    public void onItemClick(int mPosition) {
    }


    @Override
    public void AsyncResultListener(int responseCode, String result) {
        switch (responseCode){
            case 1:
                setData(result);
                adapter = new CustomAdapterForCardProject(this, list, this);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                break;
        }
    }
}