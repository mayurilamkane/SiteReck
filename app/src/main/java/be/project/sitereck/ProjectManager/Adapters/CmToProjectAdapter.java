package be.project.sitereck.ProjectManager.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.project.sitereck.GeneralInterfaces.ItemClickListener;
import be.project.sitereck.ProjectManager.DataClass.AssignCmData;
import be.project.sitereck.R;

public class CmToProjectAdapter extends RecyclerView.Adapter<CmToProjectAdapter.CTPHolder> {

    List<AssignCmData> list;
    ItemClickListener itemClickListener;

    public CmToProjectAdapter(List<AssignCmData> list, ItemClickListener itemClickListener) {
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CTPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_assigncm_pm, parent,false);
        return new CTPHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CTPHolder holder, int position) {
        AssignCmData data =  list.get(position);
        holder.Name.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CTPHolder extends RecyclerView.ViewHolder {
        TextView Name;
        ItemClickListener itemClickListener;
        public CTPHolder(@NonNull View itemView) { super(itemView); }
        public CTPHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            Name = itemView.findViewById(R.id.tv_cmname);
            this.itemClickListener = itemClickListener;
//            itemView.setOnClickListener(this);
        }
    }
}
