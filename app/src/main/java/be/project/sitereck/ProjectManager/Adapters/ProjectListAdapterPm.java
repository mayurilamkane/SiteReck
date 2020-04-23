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
        holder.startDate.setText(data.getProject_Start_date());
        holder.endDate.setText(data.getProject_End_date());
        holder.status.setText(data.getProject_status());

        if(data.getProject_status().equals("0"))
        {
            holder.status.setText("NOT STARTED");
        }
        else if(data.getProject_status().equals("1"))
        {
            holder.status.setText("COMPLETED");
        }
        else
        {
            holder.status.setText("ONGOING");
        }

    }

    @Override
    public int getItemCount() { return listItems.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView projectName, startDate,endDate,status;
        public ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) { super(itemView);    }

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            projectName = view.findViewById(R.id.tv_protitle);
            startDate = view.findViewById(R.id.tv_startdate);
            endDate = view.findViewById(R.id.tv_enddate);
            status = view.findViewById(R.id.tv_status);
            this.itemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {   itemClickListener.onClick(v,getAdapterPosition()); }
    }
}
