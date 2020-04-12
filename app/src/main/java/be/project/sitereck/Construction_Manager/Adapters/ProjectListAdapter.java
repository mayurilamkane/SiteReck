package be.project.sitereck.Construction_Manager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.R;

//import be.project.sitereck.Construction_Manager.Activities.ItemClickListener;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    private List<ProjectDataClass> listItems;
    private Context context;
    ItemClickListener itemClickListener;

    public ProjectListAdapter(List<ProjectDataClass> listItems, Context context, ItemClickListener itemClickListener) {
        this.listItems = listItems;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_project_list_item_cm,parent,false);
        return new ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectDataClass projectDataClass = listItems.get(position);
        holder.textViewHead.setText(projectDataClass.getTitle());
        holder.tv_startdate.setText(projectDataClass.getStartDate());
        holder.tv_enddate.setText(projectDataClass.getEndDate());
        holder.textViewDesc.setText(projectDataClass.getAssignBy());

        if(projectDataClass.getStatus().equals("1"))
        {
            holder.project_status.setText("Completed");
        }
        else if(projectDataClass.getStatus().equals("0"))
        {
            holder.project_status.setText("Started");
        }
        else
        {
            holder.project_status.setText("error");
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewHead,project_status;
        public TextView textViewDesc ,tv_startdate,tv_enddate;
        public ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);

            textViewHead=(TextView)itemView.findViewById(R.id.textviewHead);
            textViewDesc=(TextView)itemView.findViewById(R.id.textviewDesc);
            tv_startdate=(TextView)itemView.findViewById(R.id.tv_startdate);
            tv_enddate=(TextView)itemView.findViewById(R.id.tv_enddate);
            project_status=(TextView)itemView.findViewById(R.id.project_status);

            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
