package be.project.sitereck.ProjectManager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.ProjectManager.POJO.ActivityManagerClass_PM;
import be.project.sitereck.R;

public class ActivityMangerAdapter extends RecyclerView.Adapter<ActivityMangerAdapter.ActivityManagerHolder> {
    Context context;
    List<ActivityManagerClass_PM> list;
    ItemClickListener itemClickListener;

    public ActivityMangerAdapter(List<ActivityManagerClass_PM> list, ItemClickListener itemClickListener) {
        this.list = list;
        this.itemClickListener = itemClickListener;
    }
    public ActivityMangerAdapter(List<ActivityManagerClass_PM> list) {
        this.list = list;

    }

    public ActivityMangerAdapter() {

    }

    @NonNull
    @Override
    public ActivityManagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_activityman_pm, parent, false);
        return new ActivityManagerHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityManagerHolder holder, int position) {
        ActivityManagerClass_PM managerClass_pm = list.get(position);
        holder.title.setText(managerClass_pm.getTitle());
        holder.enddate.setText(managerClass_pm.getEndDate());
        holder.startdate.setText(managerClass_pm.getStartDate());
        holder.stat = managerClass_pm.getStatus();


        if( holder.stat == "0"){
            holder.status.setText("NOT STARTED");
        }else if( holder.stat =="1"){
            holder.status.setText("ONGOING ");
        }else if( holder.stat  == "2")
            holder.status.setText("COMPLETED");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ActivityManagerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,startdate,enddate,status;
        String stat;
        public ItemClickListener itemClickListener;
        public ActivityManagerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ActivityManagerHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            title = itemView.findViewById(R.id.tv_activity_title);
            startdate = itemView.findViewById(R.id.tv_startdate);
            enddate = itemView.findViewById(R.id.tv_enddate);
            status = itemView.findViewById(R.id.tv_status);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
