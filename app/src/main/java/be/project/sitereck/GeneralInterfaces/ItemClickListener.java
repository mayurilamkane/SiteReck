package be.project.sitereck.GeneralInterfaces;

import android.view.View;

public interface ItemClickListener {
//    void onClick(View view, int position);
    void onClick(View view, int position, boolean isLongClick);
}
