package be.project.sitereck.ProjectManager.Better.Activities.MaterialRequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import static be.project.sitereck.ProjectManager.POJO.PmMiscData.getProjectlist;
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_better_matreqitem,parent,false);
        return new ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatReqItemClass r=list.get(position);
        holder.mname.setText(r.getSmaterialName());
        holder.desc.setText(r.getNote());
        holder.proname.setText(getProjectlist().get(r.getPId()));
            if (r.getRStat().equals("0")){
                holder.approval.setText("Pending");
                holder.approval.setTextColor(context.getResources().getColor(R.color.red));
            }else if (r.getRStat().equals("1")){
                    holder.approval.setText("Approved");
                    holder.approval.setTextColor(context.getResources().getColor(R.color.primegreen));
            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mname , desc , proname , approval;
        public ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            mname = itemView.findViewById(R.id.tv_mname);
            desc = itemView.findViewById(R.id.tv_desc);
            proname = itemView.findViewById(R.id.tv_projectName);
            approval = itemView.findViewById(R.id.tv_status);

            this.itemClickListener=itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
