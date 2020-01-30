package be.project.sitereck.Construction_Manager.Adapters;

import android.app.Activity;
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

import be.project.sitereck.Construction_Manager.Activities.ItemClickListener;
import be.project.sitereck.Construction_Manager.DataClass.Activity_dataClass_cm;
import be.project.sitereck.R;

public class Activity_Adapter_cm extends RecyclerView.Adapter<Activity_Adapter_cm.ActivityViewHolder> {
    private Context context;
    private List<Activity_dataClass_cm> list;
    private Activity activity;
    private View view;
    private ItemClickListener itemClickListener;

    public Activity_Adapter_cm(Context context, List<Activity_dataClass_cm> list,ItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.card_activity_time_line_cm,null);

        return new ActivityViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {

        Activity_dataClass_cm  activity_dataClass_cm=list.get(position);
       holder.title.setText(activity_dataClass_cm.getTitle());
       holder.status.setText(activity_dataClass_cm.getStatus());
       holder.startDate.setText(activity_dataClass_cm.getStartDate());
       holder.endDate.setText(activity_dataClass_cm.getEndDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, status, startDate, endDate;
        CardView holderCard;
        LinearLayout parentlayout;
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
