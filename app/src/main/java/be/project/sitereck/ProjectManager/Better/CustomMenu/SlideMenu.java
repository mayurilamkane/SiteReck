package be.project.sitereck.ProjectManager.Better.CustomMenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import be.project.sitereck.GeneralClasses.SetSharedPrefrences;
import be.project.sitereck.ProjectManager.Activities.Profile_PM;
import be.project.sitereck.ProjectManager.Better.Activities.PM_ProjectList;
import be.project.sitereck.R;

public class SlideMenu extends AppCompatActivity {

    Activity mactivity;
    Context mcontext;
    LinearLayout project , cm , requests , profile;
    TextView name , mail ;
    ImageView closeimg;
    SetSharedPrefrences prefrences ;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.slide_menu);
//    }

//    public SlideMenu(Activity mactivity, Context mcontext) {
//        this.mactivity = mactivity;
//        this.mcontext = mcontext;
//    }

    public void FullDialog(final Activity mactivity, final Context mcontext, ViewGroup v){
        Rect disRect = new Rect();
        Window window = mactivity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(disRect);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext, R.style.CustomAlertDialog);
        ViewGroup viewGroup = v;
        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slide_menu, viewGroup, false);
        dialogView.setMinimumWidth((int)(disRect.width() * 1f));
        dialogView.setMinimumHeight((int)(disRect.height() * 1f));
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);


        prefrences = new SetSharedPrefrences(mcontext);
        project = alertDialog.findViewById(R.id.lay_projects);
        cm = alertDialog.findViewById(R.id.lay_cms);
        requests = alertDialog.findViewById(R.id.lay_req);
        profile = alertDialog.findViewById(R.id.lay_profile);
        name  = alertDialog.findViewById(R.id.username);
        mail  = alertDialog.findViewById(R.id.usermail);
        closeimg  = alertDialog.findViewById(R.id.back_icon);

        name.setText(prefrences.getVar_User_name());
        mail.setText(prefrences.getVar_User_email());

        closeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mactivity.getLocalClassName().equals("ProjectManager.Better.Activities.PM_ProjectList")) {
                    Intent intent = new Intent(mactivity, PM_ProjectList.class);
                    alertDialog.dismiss();
                    mactivity.startActivity(intent);
                }else {
                    alertDialog.dismiss();
                }
//                Toast.makeText(mactivity, "name - "+mactivity.getLocalClassName(), Toast.LENGTH_SHORT).show();

            }
        });
        cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mactivity, Profile_PM.class);
                alertDialog.dismiss();
                mactivity.startActivity(intent);
            }
        });
    }
}
