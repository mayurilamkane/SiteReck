package be.project.sitereck.ProjectManager.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.GeneralInterfaces.ItemClickListener;
import be.project.sitereck.ProjectManager.Activities.OngoingProjectPm;
import be.project.sitereck.ProjectManager.POJO.DataForCardProject;
import be.project.sitereck.R;

//import be.project.sitereck.OngoingProjectPM;

public class CustomAdapterForCardProject extends RecyclerView.Adapter<CustomAdapterForCardProject.MyViewHolder> implements Filterable {
    Activity activity;
    Context context;
    List<DataForCardProject> data,filteredData;
    View view;
    @Override
    public void onBindViewHolder(@NonNull CustomAdapterForCardProject.MyViewHolder holder, int position) {

        holder.is_Name.setText(filteredData.get(position).getPname());
        holder.is_Address.setText(filteredData.get(position).getPaddress());
        holder.is_Const.setText(filteredData.get(position).getPconengg());
        holder.is_Assign.setText(filteredData.get(position).getPassigdt());
        holder.is_Expected.setText(filteredData.get(position).getPexpdt());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

//        DashboardPm mainActivity =(DashboardPm)activity;
//        Intent intent = new Intent(mainActivity,PdfViewActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("position",position);
//        intent.putExtras(bundle);
//        mainActivity.startActivity(intent);

            }
        });
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView is_Name;
        TextView is_Address;
        TextView is_Const;
        TextView is_Assign;
        TextView is_Expected;
        public ItemClickListener itemClickListener;
        LinearLayout cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            is_Name= (TextView)itemView.findViewById(R.id.tv_name);
            is_Address=(TextView)itemView.findViewById(R.id.tv_address);
            is_Const=(TextView)itemView.findViewById(R.id.tv_construction);
            is_Assign=(TextView)itemView.findViewById(R.id.tv_assign);
            is_Expected=(TextView)itemView.findViewById(R.id.tv_expected);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
    public CustomAdapterForCardProject(Context context, List<DataForCardProject> data, Activity activity) {
        this.context = context;
        this.data = data;
        this.filteredData = data;
        this.activity = activity;
    }
    @Override
    public CustomAdapterForCardProject.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.card_project,parent,false);
        return new CustomAdapterForCardProject.MyViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
                    filteredData =data;
                }else{
                    List<DataForCardProject> filteredList = new ArrayList<>();
                    for(DataForCardProject row :data){
                        if (row.getPname().toLowerCase().contains(charString.toLowerCase())|| row.getPaddress().toLowerCase().contains(charString.toLowerCase())||row.getPconengg().toLowerCase().contains(charString.toLowerCase())|| row.getPassigdt().toLowerCase().contains(charString.toLowerCase())|| row.getPexpdt().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                    }
                    filteredData = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values =  filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<DataForCardProject>)results.values;
                notifyDataSetChanged();
            }
        };
    }
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            OngoingProjectPm sct = (OngoingProjectPm) activity;
            sct.onItemClick(mPosition);
        }
    }
}


