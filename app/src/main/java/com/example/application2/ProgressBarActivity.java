package com.example.application2;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProgressBarActivity extends AppCompatActivity {

    Button btn_start_progress_bar;
    ProgressBar pb_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_progress_bar);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        btn_start_progress_bar=findViewById(R.id.btn_start_progress_spinner);
        pb_spinner=findViewById(R.id.pb_spinner);

        btn_start_progress_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_spinner.setVisibility(View.VISIBLE);

//                Handler h = new Handler();
//                h.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        pb_spinner.setVisibility(View.INVISIBLE);
//                    }
//                },5000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i;
                        for (i=0;i<100;i++) {
                            pb_spinner.setProgress(i);
                            SystemClock.sleep(500);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb_spinner.setVisibility(View.GONE);
                            }
                        });
                    }
                }).start();

            }
        });
    }
}