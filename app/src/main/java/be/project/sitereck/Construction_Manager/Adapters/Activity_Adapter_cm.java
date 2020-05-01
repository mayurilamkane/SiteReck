package be.project.sitereck.Construction_Manager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.Construction_Manager.DataClass.Activity_dataClass_cm;
import be.project.sitereck.Construction_Manager.interfaces.ItemClickListener;
import be.project.sitereck.R;

public class Activity_Adapter_cm extends RecyclerView.Adapter<Activity_Adapter_cm.ActivityViewHolder> {
    public static ArrayList<Activity_dataClass_cm> list;

    private Context context;
    public  int status;
    public  int count=0;
    public List<String> selectedId = new ArrayList<>();
    //private Activity activity;
    //private View view;
     ItemClickListener itemClickListener;

    public Activity_Adapter_cm(Context context, ArrayList<Activity_dataClass_cm> list, ItemClickListener itemClickListener) {
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
    public void onBindViewHolder(@NonNull final ActivityViewHolder holder, int position) {

        final Activity_dataClass_cm activity_dataClass_cm=list.get(position);
       holder.title.setText(activity_dataClass_cm.getTitle());
       holder.startDate.setText(activity_dataClass_cm.getStartDate());
       holder.endDate.setText(activity_dataClass_cm.getEndDate());
       holder.checkBox.setText( "check" + position);
       holder.checkBox.setChecked(list.get(position).getSelected());
       holder.checkBox.setTag(position);


        holder.checkBox.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) holder.checkBox.getTag();
               // Toast.makeText(context, list.get(pos).getP_act_id() + "Activity clicked!", Toast.LENGTH_SHORT).show();

                    if (list.get( pos ).getSelected()) {
                        list.get( pos ).setSelected( false );
                        status = 0;

                       // Toast.makeText( context, status + " clicked!", Toast.LENGTH_SHORT ).show();

                    } else {

                            list.get( pos ).setSelected( true );
                            if (activity_dataClass_cm.getStatus().equals( "1" )) {
                                Toast.makeText( context, "Activity Is Already Completed \n Deselect The Activity", Toast.LENGTH_SHORT ).show();
                                }
                                else{
                                status = 1;
                                count++;
                                //Toast.makeText( context, status + " clicked!", Toast.LENGTH_SHORT ).show();
                                }

                            }
                        }
        });

        if(activity_dataClass_cm.getStatus().equals("0")){
            holder.status.setText("Not Started ");
            holder.status.setTextColor(context.getResources().getColor(R.color.red));
        } else if(activity_dataClass_cm.getStatus().equals("1")){
            holder.status.setText(" completed ");
            holder.status.setTextColor(context.getResources().getColor(R.color.blue_300));
        }else {
            holder.status.setText(" Completed ");
            holder.status.setTextColor(context.getResources().getColor(R.color.blue_300));
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
        public CheckBox checkBox;

        public ActivityViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_activity_title);
            status=itemView.findViewById(R.id.tv_status);
            startDate=itemView.findViewById(R.id.tv_startdate);
            endDate=itemView.findViewById(R.id.tv_enddate);
            holderCard = (CardView) itemView.findViewById(R.id.cd_view);
            parentlayout = (LinearLayout) itemView.findViewById(R.id.lin_card_layout);
            checkBox= (CheckBox) itemView.findViewById(R.id.checkbox_sub_activity);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
