package be.project.sitereck.ProjectManager.Activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import be.project.sitereck.ProjectManager.Adapters.CustomAdapterForCard;
import be.project.sitereck.ProjectManager.POJO.DataForCard;
import be.project.sitereck.R;

public class ActivityPm extends AppCompatActivity {
    RecyclerView recyclerView;
    static List<DataForCard> list;
    static CustomAdapterForCard adapter;
    String Title[];
    String Desc[];
    EditText edt_act,edt_subact;
    FloatingActionButton btn_add;
    Button btn_complete;

    HelperClass helperClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm);
        initElements();
        list = new ArrayList<>();
        adapter = new CustomAdapterForCard(this,list,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(adapter);
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new DataForCard(helperClass.getStringEditText(edt_act),helperClass.getStringEditText(edt_subact)));
                adapter.notifyDataSetChanged();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJSON();
            }
        });
    }

    private void makeJSON() {
        JSONObject ACTIVITY;
        JSONArray ACTIVITIES_ARRAY = new JSONArray();
        JSONArray SUB_ACT_ARRAY = new JSONArray();
        ArrayList<String> all_activities = new ArrayList<String>();
        JSONObject finalObject = new JSONObject();

        for (DataForCard c:list) {
            all_activities.add(c.getTitle());
        }
        HashSet<String> unique_activities = new HashSet(all_activities);

        System.out.println("ArrayList Unique Values");

        //iterate through HashSet
        for(String strNumber : unique_activities){
//            System.out.println(strNumber);
            ACTIVITY = new JSONObject();
            try {
                ACTIVITY.put("activity_name",strNumber);
                ACTIVITIES_ARRAY.put(ACTIVITY);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//
        for (DataForCard c:list) {
            ACTIVITY = new JSONObject();
            try {
                ACTIVITY.put("activity_name",c.getTitle());
                ACTIVITY.put("sub_activity_name",c.getDesc());
                SUB_ACT_ARRAY.put(ACTIVITY);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("JSON ARRAY ACTIVITIES", "makeJSON: "+SUB_ACT_ARRAY.toString());

        try {
            finalObject.put("activities",ACTIVITIES_ARRAY);
            finalObject.put("sub_activities",SUB_ACT_ARRAY);
            Log.d("FINAL JSON OBJECT", "makeJSON: "+finalObject.toString());
            new sendSyncData().execute(finalObject.toString());
        }catch (Exception e){

        }

    }

    private void initElements() {
        helperClass=new HelperClass();
        recyclerView = (RecyclerView) findViewById(R.id.recy_product);
        edt_act = (EditText) findViewById(R.id.edt_activity);
        edt_subact = (EditText) findViewById(R.id.edt_subactivity);
        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
        btn_complete = (Button)findViewById(R.id.btn_complete);
    }
    class sendSyncData extends AsyncTask<String,String,String> {
        ProgressDialog loading;

        @Override
        protected String doInBackground(String... params) {
            String DATA = params[0];
            String login_url = "https://sitereck-1.000webhostapp.com/API/insertNewActivity.php";

            try {


                URL url = new URL(login_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //httpURLConnection.setConnectTimeout(15000);
                //  httpURLConnection.setReadTimeout(15000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(DATA);
                //Log.i(MainActivity.class.toString(), jsonObject.toString());
                writer.flush();
                writer.close();
                os.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;


            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading=ProgressDialog.show(ActivityPm.this,"Please Wait","CREATING YOUR ACTIVITIES",true,false);
        }

        @Override
        protected void onPostExecute(String s) {
            loading.dismiss();
            Toast.makeText(ActivityPm.this,"Your Activity is Inserted Successfully ", Toast.LENGTH_SHORT).show();
        }

    }
}
