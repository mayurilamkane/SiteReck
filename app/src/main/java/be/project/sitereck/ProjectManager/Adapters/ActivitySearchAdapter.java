package be.project.sitereck.ProjectManager.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import be.project.sitereck.ProjectManager.POJO.Activities;
import be.project.sitereck.R;

public class ActivitySearchAdapter extends ArrayAdapter<Activities> {
    private final int resourceId;
    private Context context;
    private List<Activities> items , tempItems , suggestions;
    public  ActivitySearchAdapter(@NonNull Context context, int resourceId ,List<Activities> items){
        super(context, resourceId,items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null){
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId,parent,false);
            }
            Activities activities = getItem(position);
            TextView name  = view.findViewById(R.id.textViewrow);
            name.setText(activities.getName());

        }catch (Exception e ){
            System.out.println(e);
        }

        return view;
    }

    @Nullable
    @Override
    public Activities getItem(int position){
        return items.get(position);
    }
    @Override
    public int getCount() {
        return items.size();
    }@Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter(){
        return actlistFilter;
    }
    private Filter actlistFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Activities activities = (Activities) resultValue;
            return activities.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null){
                suggestions.clear();
                for (Activities activities:tempItems){
                    if (activities.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(activities);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count= suggestions.size();
                return  filterResults;
            }else{
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Activities> tempValues = (ArrayList<Activities>) results.values;

            if (results != null && results.count > 0){
                clear();
                for (Activities activities : tempValues){ add(activities);}
                notifyDataSetChanged();
            }else {
                clear();
                notifyDataSetChanged();
            }
        }
    };

}
