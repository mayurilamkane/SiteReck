package be.project.sitereck.ProjectManager.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.List;

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
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

    @NonNull
    @Override
    public CTPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_assigncm_pm, parent,false);
        return new CTPHolder(view , itemClickListener);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull CTPHolder holder, int position) {
        final AssignCmData data =  list.get(position);
        holder.Name.setText(data.getName());
        holder.Email.setText(data.getEmail());
        holder.Contact.setText(data.getContact());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CTPHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Name,Email,Contact;
        ItemClickListener itemClickListener;

        public CTPHolder(@NonNull View itemView) { super(itemView);
            Name = itemView.findViewById(R.id.tv_cmname);

        }
        public CTPHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            Name = itemView.findViewById(R.id.tv_cmname);
            Email = itemView.findViewById(R.id.tv_email);
            Contact = itemView.findViewById(R.id.tv_contact);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {   itemClickListener.onClick(v,getAdapterPosition());  }
    }
}
