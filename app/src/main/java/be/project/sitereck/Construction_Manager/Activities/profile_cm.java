package be.project.sitereck.Construction_Manager.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

import be.project.sitereck.R;

import static android.view.View.*;

public class profile_cm extends AppCompatActivity implements View.OnClickListener {

    ImageView image;
    Button btn_edit;
private  static  final  int PICK_IMAGE=1;
Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_cm);
        image=(ImageView)findViewById(R.id.profile_image);
        btn_edit=( Button)findViewById(R.id.edit_btn);
        btn_edit.setOnClickListener((View.OnClickListener)this);
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallary=new Intent();
                gallary.setType("image/ + ");
                gallary.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallary,"sellect picture"),PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE &&requestCode==RESULT_OK){
            imageUri=data.getData();
            try {
                Bitmap Bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                image.setImageBitmap(Bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_btn:
                Intent intent = new Intent(profile_cm.this, Edit_profile_cm.class);
                startActivity(intent);
        }
    }
}
