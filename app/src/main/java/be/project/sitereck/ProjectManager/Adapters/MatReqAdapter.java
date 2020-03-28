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
import be.project.sitereck.ProjectManager.POJO.MatReqItemClass;
import be.project.sitereck.R;

public class MatReqAdapter extends RecyclerView.Adapter<MatReqAdapter.ViewHolder> {
    private List<MatReqItemClass> list;
    private Context context;
    ItemClickListener itemClickListener;

    public MatReqAdapter(List<MatReqItemClass> list, Context context, ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_matreq_pm,parent,false);
        return new ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatReqItemClass r=list.get(position);
        holder.material_name.setText(r.getSmaterial());
        holder.req_date.setText(r.getMdate());
        holder.proname.setText(r.getProjectName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView material_name,req_date,proname;
        public ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            material_name=(TextView)itemView.findViewById(R.id.tv_material_name);
            req_date=(TextView)itemView.findViewById(R.id.tv_req_date);
            proname=(TextView)itemView.findViewById(R.id.tv_matpro);
            this.itemClickListener=itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
