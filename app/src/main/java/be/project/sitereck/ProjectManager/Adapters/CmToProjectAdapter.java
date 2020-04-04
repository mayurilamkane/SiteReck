package be.project.sitereck.ProjectManager.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.Activities.AssignCmToProject_PM;
import be.project.sitereck.ProjectManager.Activities.ProjCMList;
import be.project.sitereck.ProjectManager.POJO.AssignCmData;
import be.project.sitereck.R;

public class CmToProjectAdapter extends RecyclerView.Adapter<CmToProjectAdapter.CTPHolder> {
//TODO: complete adapter and cmlist for project
    List<AssignCmData> list;
    Context context;
    ItemClickListener itemClickListener;
    public int Pid;
    ProgressDialog pd;
    RequestQueue rq;

    public CmToProjectAdapter(List<AssignCmData> list,Context context, ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }
    public CmToProjectAdapter(List<AssignCmData> list,Context context) {
        this.list = list;
        this.context = context;
    }
    public CmToProjectAdapter(List<AssignCmData> list,Context context, int pid) {
        this.list = list;
        this.context = context;
        Pid = pid;
    }

    @NonNull
    @Override
    public CTPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_assigncm_pm, parent,false);
        return new CTPHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull CTPHolder holder, int position) {
        final AssignCmData data =  list.get(position);
        holder.button.setBackground(context.getDrawable(Color.RED));
        holder.button.setText("Remove");
        holder.Name.setText(data.getName());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveCM(Pid,data.getId());
            }
        });

    }

    private void RemoveCM(final int pid, final String id) {
        pd = ProgressDialog.show(context,null,"Removing Manager");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCallRcm(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("tagconvertstr", "["+response+"]");
                System.out.println("response --> "+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(pd!=null && pd.isShowing())
                    pd.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("p_id", String.valueOf(pid));
                params.put("u_id",id);
                return params;
            }
        };
        rq= Volley.newRequestQueue(context);
        rq.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CTPHolder extends RecyclerView.ViewHolder {
        TextView Name;
        Button button;
        ItemClickListener itemClickListener;
        public CTPHolder(@NonNull View itemView) { super(itemView);
            Name = itemView.findViewById(R.id.tv_cmname);
            button = itemView.findViewById(R.id.btn_addcm);
        }
        public CTPHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            Name = itemView.findViewById(R.id.tv_cmname);
            this.itemClickListener = itemClickListener;
//            itemView.setOnClickListener(this);
        }
    }
}
