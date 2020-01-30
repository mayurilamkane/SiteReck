package be.project.sitereck.Construction_Manager.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.project.sitereck.Construction_Manager.Activities.ItemClickListener;
import be.project.sitereck.Construction_Manager.DataClass.SubActivityClass_CM;
import be.project.sitereck.R;

public class SubActivityAdapter_CM extends RecyclerView.Adapter<SubActivityAdapter_CM.SubActHolder> {

    List<SubActivityClass_CM> list;
    private ItemClickListener mitemClickListener;

    public SubActivityAdapter_CM(List<SubActivityClass_CM> list,ItemClickListener itemClickListener) {
        this.mitemClickListener = itemClickListener;
        this.list=list;
    }

    @NonNull
    @Override
    public SubActHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_subactivity_cm,parent,false);
        return new SubActHolder(view,mitemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubActHolder holder, int position) {
            holder.Title.setText(list.get(position).getSubActivityTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubActHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CheckBox Title;
        private ItemClickListener itemClickListener;
        public SubActHolder(@NonNull View itemView,ItemClickListener itemClickListener)
        {
            super(itemView);
            Title=(CheckBox)itemView.findViewById(R.id.cd_sub_activity);
            this.itemClickListener=mitemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
