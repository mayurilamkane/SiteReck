package be.project.sitereck.ProjectManager;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import be.project.sitereck.R;
/// NOT USED YET
public class DialogGenerator {
    Dialog dialog;
    Context context;

    public DialogGenerator(Context context) {
        this.context = context;
    }
    public void Generatedialog(){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_projectlist_filter);
        LinearLayout l1 = dialog.findViewById(R.id.ll);
        l1.setBackgroundResource(R.drawable.dialog_bg);

        TextView all, ong,comp,nons;
        all = dialog.findViewById(R.id.item_allproject);
        ong = dialog.findViewById(R.id.item_onngoing);
        comp = dialog.findViewById(R.id.item_completed);
        nons = dialog.findViewById(R.id.item_notstarted);

        dialog.show();
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked all", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        ong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked ongoing", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked completed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        nons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked nons", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
