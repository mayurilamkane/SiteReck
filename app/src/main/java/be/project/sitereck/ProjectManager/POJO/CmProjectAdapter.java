package be.project.sitereck.ProjectManager.POJO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.R;

public class CmProjectAdapter extends RecyclerView.Adapter<CmProjectAdapter.ViewHolder>{
    List<DataForCardProject> listItems;
    Context context;
    ItemClickListener itemClickListener;

    public CmProjectAdapter(List<DataForCardProject> listItems, Context context, ItemClickListener itemClickListener) {
        this.listItems = listItems;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_project_list_cm,parent,false);
        return new ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataForCardProject data = listItems.get(position);
        holder.name.setText(data.getPname());
        holder.sdate.setText(data.getPassigdt());
        holder.endate.setText(data.getPexpdt());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name , sdate,endate;
        ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            name = view.findViewById(R.id.tv_protitle);
            sdate = view.findViewById(R.id.tv_startdate);
            endate = view.findViewById(R.id.tv_enddate);
            this.itemClickListener=itemClickListener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
