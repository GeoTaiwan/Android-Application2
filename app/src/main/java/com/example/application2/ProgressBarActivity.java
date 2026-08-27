package com.example.application2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProgressBarActivity extends AppCompatActivity {

    Button btn_start_progress_bar,getBtn_start_progress_bar_horizontal,btn_progress_dialog,btn_custom_progress_dialog;
    ProgressBar pb_spinner,pb_horizontal;
    TextView tv_msg;
    int percentage;

    ProgressDialog pd;
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
        btn_start_progress_bar = findViewById(R.id.btn_start_progress_spinner);
        getBtn_start_progress_bar_horizontal=findViewById(R.id.btn_start_progress_horizontal);
        btn_progress_dialog=findViewById(R.id.btn_start_progress_dialog);
        btn_custom_progress_dialog=findViewById(R.id.btn_start_progress_custom_dialog);
        pb_spinner = findViewById(R.id.pb_spinner);
        pb_horizontal=findViewById(R.id.pb_horizontal);
        tv_msg = findViewById(R.id.tv_progress_output);

        btn_start_progress_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_spinner.setVisibility(View.VISIBLE);
                pb_horizontal.setMax(100);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (percentage = 0; percentage <= 100; percentage++) {
                            pb_spinner.setProgress(percentage);
                            pb_horizontal.setProgress(percentage);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_msg.setText("目前進度：" + percentage + "%");
                                }
                            });

                            SystemClock.sleep(500);

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb_spinner.setVisibility(View.GONE);
                            }
                        });
                        pb_horizontal.setProgress(0);
                    }
                }).start();

            }
        });

        getBtn_start_progress_bar_horizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_horizontal.setMax(100);
//                pb_spinner.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (percentage = 0; percentage <= 100; percentage++) {
                            pb_horizontal.setProgress(percentage);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_msg.setText("目前進度：" + percentage + "%");
                                }
                            });

                            SystemClock.sleep(300);

                        }
                        pb_horizontal.setProgress(0);
                    }
                }).start();
            }
        });

        btn_progress_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(ProgressBarActivity.this);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setTitle("進度執行中…");
                pd.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (percentage = 0; percentage <= 100; percentage++) {
                            pd.setMessage("目前進度：" + percentage + "%");
                            SystemClock.sleep(300);
                        }
                        pd.dismiss();
                    }
                }).start();


            }
        });

        btn_custom_progress_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProgressBarActivity.this);
                builder.setTitle("進度執行中…");
                builder.setView(R.layout.progress_dialog_layout);
                AlertDialog progressDialog = builder.create();

                Context c = progressDialog.getContext();
                LayoutInflater inflater = LayoutInflater.from(c);
                View progress_dialog_layout = inflater.inflate(R.layout.progress_dialog_layout,
                        (ViewGroup) findViewById(R.id.progress_dialog_layout));
                TextView tv_progress = (TextView) progress_dialog_layout.findViewById(R.id.tv_progress);
                progressDialog.setView(progress_dialog_layout);
                progressDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (percentage=0;percentage<=100;percentage++){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_progress.setText("目前進度：" + percentage + "%");
                                }
                            });
                            SystemClock.sleep(100);
                        }
                        progressDialog.dismiss();
                    }
                }).start();

            }
        });

    }
}