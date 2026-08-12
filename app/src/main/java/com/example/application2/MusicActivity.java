package com.example.application2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MusicActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    Button btn_play, btn_pause, btn_stop, btn_addTime;
    TextView tv_status;
    MediaPlayer myPlayer;
    int intPauseTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        btn_play = findViewById(R.id.btn_start);
        btn_pause = findViewById(R.id.btn_pause);
        btn_stop = findViewById(R.id.btn_stop);
        btn_addTime =findViewById(R.id.btn_addTime);
        tv_status = findViewById(R.id.tv_status);
        myPlayer = MediaPlayer.create(MusicActivity.this, R.raw.airball);
        myPlayer.setOnCompletionListener(this);

        btn_addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPlayer.seekTo(myPlayer.getCurrentPosition()+100000);
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPlayer != null) {
                    //播放器不為空
                    if (!myPlayer.isPlaying()) {
                        //播放器目前不在播放中
                        myPlayer.start();
                        if (intPauseTime > 0) {
                            tv_status.setText("播放狀態：音樂從 " + intPauseTime + " 位置接續播放…");
                        } else {
                            tv_status.setText("播放狀態：音樂播放中…");
                        }
                    }
                }
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPlayer != null) {
                    //播放器不為空
                    if (myPlayer.isPlaying()) {
                        //播放器播放中
                        myPlayer.pause();
                        intPauseTime = myPlayer.getCurrentPosition();
                        tv_status.setText("播放狀態：音樂暫停，暫停位置 " + intPauseTime + " ms");
                    }
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("StopOnClick", "onClick開始");
                if (myPlayer != null) {
                    //播放器不為空
                    if (myPlayer.isPlaying()) {
                        //播放器播放中
                        Log.d("StopOnClick", "stop()執行");
                        myPlayer.stop();
                        tv_status.setText("播放狀態：音樂停止");

                        try {
                            Log.d("StopOnClick", "prepareAsync()執行");
                            myPlayer.prepareAsync();
                        } catch (IllegalStateException e) {
                            tv_status.setText("發生錯誤：" + e.getMessage());
                            throw new RuntimeException(e);
                        }
                    }
                }
                Log.d("StopOnClick", "onClick結束");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MusicActivity", "onDestroy開始");
        if (myPlayer != null) {
            if (myPlayer.isPlaying()) {
                myPlayer.stop();
            }
            myPlayer.release();
        }
        myPlayer = null;
        Log.d("MusicActivity", "onDestroy結束");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MusicActivity", "onPause開始");
        if (myPlayer != null) {
            if (myPlayer.isPlaying()) {
                myPlayer.pause();
                intPauseTime = myPlayer.getCurrentPosition();
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MusicActivity", "onResume開始");
        if (myPlayer != null) {
            if (!myPlayer.isPlaying() && intPauseTime != 0) {
                myPlayer.seekTo(intPauseTime);
                myPlayer.start();
                tv_status.setText("播放狀態：音樂從 " + intPauseTime + " 位置接續播放…(onResume)");
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_status.setText("播放狀態：音樂從 " + intPauseTime + " 位置接續播放…");
                    }
                },5000);

            }
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d("onCompletion", "onCompletion開始");
        tv_status.setText("播放狀態：整首音樂播放完畢。");
        intPauseTime=0;
        myPlayer.seekTo(intPauseTime);
        Log.d("onCompletion", "onCompletion結束");
    }



}

//        if (myPlayer != null) {
//            if (myPlayer.isPlaying()) {
//                Log.d("onCompletion", "myPlayer是否播放中：" + (myPlayer.isPlaying()));
//                myPlayer.stop();
//            }
//            myPlayer.seekTo(0)            ;
//            myPlayer.reset();
//            try {
//                myPlayer.prepareAsync();
//            } catch (IllegalStateException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }