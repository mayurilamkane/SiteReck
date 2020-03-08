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
import be.project.sitereck.ProjectManager.POJO.DataForCard;
import be.project.sitereck.R;


public class CustomAdapterForCard extends RecyclerView.Adapter<CustomAdapterForCard.MyViewHolder> implements Filterable {
    Activity activity;
    Context context;
    List<DataForCard> data,filteredData;
    View view;
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
                    filteredData =data;
                }else{
                    List<DataForCard> filteredList = new ArrayList<>();
                    for(DataForCard row :data){
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())|| row.getDesc().toLowerCase().contains(charString.toLowerCase())){
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
                filteredData = (ArrayList<DataForCard>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public CustomAdapterForCard.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.card_activity_pm,parent,false);
        return new CustomAdapterForCard.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.is_Activity.setText(filteredData.get(position).getTitle());
        holder.is_subActivity.setText(filteredData.get(position).getDesc());
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

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView is_Activity;
        TextView is_subActivity;
        public ItemClickListener itemClickListener;
        LinearLayout cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            is_Activity= (TextView)itemView.findViewById(R.id.tv_act);
            is_subActivity=(TextView)itemView.findViewById(R.id.tv_subact);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {itemClickListener.onClick(v,getAdapterPosition(),false);}

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
    public CustomAdapterForCard(Context context, List<DataForCard> data, Activity activity) {
        this.context = context;
        this.data = data;
        this.filteredData = data;
        this.activity = activity;
    }

}
