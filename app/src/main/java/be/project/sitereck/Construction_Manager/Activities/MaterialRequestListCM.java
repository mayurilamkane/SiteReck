package be.project.sitereck.Construction_Manager.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.project.sitereck.Construction_Manager.Adapters.MaterialListAdapter;
import be.project.sitereck.Construction_Manager.DataClass.RequestitemClass_CM;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.R;

public class MaterialRequestListCM extends AppCompatActivity implements ItemClickListener, View.OnClickListener {

    RecyclerView repolist;
    MaterialListAdapter adapter;
    List<RequestitemClass_CM> list=new ArrayList<>();
    RequestQueue requestQueue;
    String HTTP_JSON_URL="https://sitereck-1.000webhostapp.com/API/getMaterialList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_request_list_cm);

        repolist=(RecyclerView)findViewById(R.id.repolist);
//        requestQueue.getCache().clear();
        JSON_DATA_WEB_CALL();

        adapter=new MaterialListAdapter(list,this,this);
        repolist.setLayoutManager(new LinearLayoutManager(this));


    }

    private void JSON_DATA_WEB_CALL() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, HTTP_JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    requestQueue.getCache().clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("activities");
                    System.out.println("array = "+array);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        RequestitemClass_CM r = new RequestitemClass_CM(object.getString("req_material"), object.getString("req_required_date"));
                        list.add(r);
//                        Toast.makeText(MaterialRequestListCM.this, object.getString("req_material"), Toast.LENGTH_SHORT).show();
                        System.out.println("list = "+ list.get(i));
                    }
                    repolist.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MaterialRequestListCM.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("proj_id","1") ;

                return super.getParams();
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onClick(View v, int adapterPosition) {
          //  list.get(adapterPosition);
        //Intent intent=new Intent();
    }
}
