package be.project.sitereck.Construction_Manager.interfaces;

import android.view.View;

public interface ItemClickListener {
    void onRefresh();

    void onClick(View v, int adapterPosition);
}
