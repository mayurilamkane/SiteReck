package be.project.sitereck.GeneralActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import be.project.sitereck.R;

public class MainActivity extends AppCompatActivity {
                 Button Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  Button=(Button)findViewById(R.id.btn_location);

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_location:
                        Intent intent = new Intent(MainActivity.this, particular_project.class);
                        startActivity(intent);
                }
                    }
        });*/
    }

}
