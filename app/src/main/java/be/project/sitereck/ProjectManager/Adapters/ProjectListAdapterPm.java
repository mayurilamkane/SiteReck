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
import be.project.sitereck.ProjectManager.POJO.ProjectData;
import be.project.sitereck.R;

public class ProjectListAdapterPm extends RecyclerView.Adapter<ProjectListAdapterPm.ViewHolder> {

    private List<ProjectData> listItems;
    private Context context;
    ItemClickListener itemClickListener;

    public ProjectListAdapterPm(List<ProjectData> listItems, Context context, ItemClickListener itemClickListener) {
        this.listItems = listItems;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_projectlist_item_pm,parent,false);
        return new ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectData data = listItems.get(position);
        holder.projectName.setText(data.getProject_name());
//        holder.remainingday.setText(data.getProject_Start_date());
        holder.Address.setText(data.getProject_Address());
//        holder.status.setText(data.getProject_status());

        if(data.getProject_status().equals("0"))
        {
            if ( (Integer.valueOf(data.getDiff()) >= 0 )){
                holder.status.setText("NOT STARTED");
                holder.remainingday.setText(data.getDiff());
            }else{
                holder.status.setText("NOT STARTED ");
                holder.status.setTextColor(context.getResources().getColor(R.color.red));
                holder.remainingday.setText(Integer.valueOf(data.getDiff())*-1 + "days");
                holder.tvremday.setText("Lagging By -");
                holder.remainingday.setTextColor(context.getResources().getColor(R.color.red));
            }

        }
        else if(data.getProject_status().equals("1"))
        {
            holder.status.setText("COMPLETED");
            holder.status.setTextColor(context.getResources().getColor(R.color.blue_300));
            holder.remainingday.setText(data.getProject_status_date());
            holder.tvremday.setText("completed On -");
            holder.remainingday.setTextColor(context.getResources().getColor(R.color.blue_300));
        }
        else
        {
            if ( (Integer.valueOf(data.getDiff()) >= 0 )){
                holder.status.setText("On Going");
                holder.status.setTextColor(context.getResources().getColor(R.color.blue_300));
                holder.remainingday.setText(data.getDiff());
                holder.remainingday.setTextColor(context.getResources().getColor(R.color.blue_300));
            }else{
                holder.status.setText("On Going");
                holder.status.setTextColor(context.getResources().getColor(R.color.primegreen));
                holder.remainingday.setText(Integer.valueOf(data.getDiff())*-1 + "days");
                holder.tvremday.setText("Lagging By -");
                holder.remainingday.setTextColor(context.getResources().getColor(R.color.red));
            }
        }

    }

    @Override
    public int getItemCount() { return listItems.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView projectName, remainingday,Address,status, tvremday;
        public ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) { super(itemView);    }

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            projectName = view.findViewById(R.id.tv_protitle);
            remainingday = view.findViewById(R.id.remainingday);
            Address = view.findViewById(R.id.tv_address);
            tvremday = view.findViewById(R.id.tv_remday);
            status = view.findViewById(R.id.tv_status);
            this.itemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {   itemClickListener.onClick(v,getAdapterPosition()); }
    }
}
