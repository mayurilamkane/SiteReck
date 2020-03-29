package be.project.sitereck.Construction_Manager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.project.sitereck.Construction_Manager.DataClass.Activity_dataClass_cm;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.R;

public class Activity_Adapter_cm extends RecyclerView.Adapter<Activity_Adapter_cm.ActivityViewHolder> {
    private List<Activity_dataClass_cm> list;
    private Context context;
    //private Activity activity;
    //private View view;
     ItemClickListener itemClickListener;

    public Activity_Adapter_cm(Context context, List<Activity_dataClass_cm> list, ItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater=LayoutInflater.from(context);
        //View view=inflater.inflate(R.layout.card_activity_time_line_cm,null);
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_activity_time_line_cm,parent,false);

        return new ActivityViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {



        Activity_dataClass_cm activity_dataClass_cm=list.get(position);
       holder.title.setText(activity_dataClass_cm.getTitle());
       holder.startDate.setText(activity_dataClass_cm.getStartDate());
       holder.endDate.setText(activity_dataClass_cm.getEndDate());

        if(activity_dataClass_cm.getStatus().equals("0")){
            holder.status.setText("Not Started ");
        } else if(activity_dataClass_cm.getStatus().equals("1")){
            holder.status.setText(" Started ");
        }else {
            holder.status.setText(" Completed ");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, status, startDate, endDate;
        public CardView holderCard;
        public LinearLayout parentlayout;
        public ItemClickListener itemClickListener;

        public ActivityViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_activity_title);
            status=itemView.findViewById(R.id.tv_status);
            startDate=itemView.findViewById(R.id.tv_startdate);
            endDate=itemView.findViewById(R.id.tv_enddate);
            holderCard = (CardView) itemView.findViewById(R.id.cd_view);
            parentlayout = (LinearLayout) itemView.findViewById(R.id.lin_card_layout);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
