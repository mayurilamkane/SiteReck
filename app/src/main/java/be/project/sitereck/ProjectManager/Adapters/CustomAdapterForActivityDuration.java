package be.project.sitereck.ProjectManager.Adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;


import be.project.sitereck.GeneralInterfaces.ItemClickListener;
import be.project.sitereck.ProjectManager.POJO.DataActivityDuration;
import be.project.sitereck.R;

//import be.project.sitereck.DataActivityDuration;


public class CustomAdapterForActivityDuration extends RecyclerView.Adapter<CustomAdapterForActivityDuration.MyViewHolder> {
    Activity activity;
    Context context;
    List<DataActivityDuration> data,filteredData;
    View view;

    @NonNull
    @Override
    public CustomAdapterForActivityDuration.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.card_activity_duration,parent,false);
        return new CustomAdapterForActivityDuration.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//        holder.is_Activity.setText(filteredData.get(position).get());
//        holder.is_subActivity.setText(filteredData.get(position).getDesc());
        holder.tv_activity.setText(filteredData.get(position).getActivity_name());

        holder.btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int MONTH = c.get(Calendar.MONTH);
                final int YEAR = c.get(Calendar.YEAR);
                final int DAY = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        holder.btn_start.setText(String.valueOf(dayOfMonth+" - "+(month+1)+" - "+year));

                        filteredData.get(position).setStart_date(String.valueOf(dayOfMonth+" - "+(month+1)+" - "+year));
                    }
                },YEAR,MONTH,DAY);
                dialog.show();
            }
        });
        holder.btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int MONTH = c.get(Calendar.MONTH);
                final int YEAR = c.get(Calendar.YEAR);
                final int DAY = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        holder.btn_end.setText(String.valueOf(dayOfMonth+" - "+(month+1)+" - "+year));
                        filteredData.get(position).setEnd_date(String.valueOf(dayOfMonth+" - "+(month+1)+" - "+year));
                    }
                },YEAR,MONTH,DAY);
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView tv_activity;
//        TextView is_subActivity;
        Button btn_start,btn_end;
        public ItemClickListener itemClickListener;
        LinearLayout cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_activity= (TextView)itemView.findViewById(R.id.tv_act_name);
//            is_subActivity=(TextView)itemView.findViewById(R.id.tv_subact);
            btn_start = (Button) itemView.findViewById(R.id.btn_startDate);
            btn_end= (Button) itemView.findViewById(R.id.btn_endDate);

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
    public CustomAdapterForActivityDuration(Context context, List<DataActivityDuration> data, Activity activity) {
        this.context = context;
        this.data = data;
        this.filteredData = data;
        this.activity = activity;
    }

}
