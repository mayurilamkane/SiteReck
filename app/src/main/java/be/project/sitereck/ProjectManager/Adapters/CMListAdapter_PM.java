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
import be.project.sitereck.ProjectManager.Activities.AllCmList_PM;
import be.project.sitereck.ProjectManager.POJO.CMInfoDataClass;
import be.project.sitereck.R;

public class CMListAdapter_PM extends RecyclerView.Adapter<CMListAdapter_PM.ViewHolder> {
    List<CMInfoDataClass> listItems;
    Context context;
    ItemClickListener itemClickListener;

    public CMListAdapter_PM(List<CMInfoDataClass> listItems, Context context, ItemClickListener itemClickListener) {
        this.listItems = listItems;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cm_list,parent,false);
        return new ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CMInfoDataClass dataClass = listItems.get(position);
        holder.tvName.setText(dataClass.getCMName());
        holder.tvCount.setText("Project Count"+dataClass.getCount());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName , tvCount;
        ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            tvName = view.findViewById(R.id.tv_cmname);
            tvCount = view.findViewById(R.id.tv_cmcp);
            this.itemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
