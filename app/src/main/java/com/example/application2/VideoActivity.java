package com.example.application2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Field;

public class VideoActivity extends AppCompatActivity {
    VideoView vv_player;
    TextView tv_title;
    MediaController media_controller;

    String uriResourceTitle = "android.resource://";
    String videoFileName = "point21";
    String strVideoResourceURI;
    int intVideoResourceId;
    int intPausePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SelfDefinedLog", "onCreate開始");
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        vv_player = findViewById(R.id.vv_player);
        tv_title = findViewById(R.id.tv_title);

        media_controller = new MediaController(VideoActivity.this);

        intVideoResourceId = R.raw.point21;
//        intVideoResourceId = getResources().getIdentifier(videoFileName,"raw",getPackageName());
//        intVideoResourceId=getResId(videoFileName,R.raw.class);

//        Log.d("VideoActivity","onCreate結束");
        strVideoResourceURI = uriResourceTitle + getPackageName() + "/" + intVideoResourceId;
        vv_player.setVideoURI(Uri.parse(strVideoResourceURI));
        vv_player.setMediaController(media_controller);
//        vv_player.start();
    }

    public static int getResId(String resName, Class<?> c){
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SelfDefinedLog", "onPause: Activity被切到背景時觸發");
        if (vv_player!=null){
            if (vv_player.isPlaying()){
                Log.d("SelfDefinedLog", "onPause: 有播放器且播放中");
                intPausePosition = vv_player.getCurrentPosition();
                Log.d("SelfDefinedLog", "onPause: 取得目前進度存於intPausePosition中");
                vv_player.pause();
                Log.d("SelfDefinedLog", "onPause: 暫停播放");
                tv_title.setText("影片播放器：播放暫停於[" + String.valueOf(intPausePosition/1000) + "." +
                        String.valueOf(intPausePosition%1000) + "]秒");
                Log.d("SelfDefinedLog", "onPause: 更新標題");
                hasPaused=true;
            }
        }
    }
boolean hasPaused=false;
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SelfDefinedLog", "onResume: Activity回到前景時觸發");
        if (vv_player!=null){
            Log.d("SelfDefinedLog", "onResume: 播放器存在");
            if(intPausePosition!=0){
                Log.d("SelfDefinedLog", "onResume: 進度不為0");
                vv_player.seekTo(intPausePosition);
                Log.d("SelfDefinedLog", "onResume: 調整進度至上次暫停點");
            }
            if (hasPaused) {
                vv_player.start();
                Log.d("SelfDefinedLog", "onResume: 繼續播放");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SelfDefinedLog", "onDestroy: 開始");
        if (vv_player!=null){
            Log.d("SelfDefinedLog", "onDestroy: 播放器存在");
            if (vv_player.isPlaying()){
                Log.d("SelfDefinedLog", "onDestroy: 播放中");
                vv_player.stopPlayback();
                Log.d("SelfDefinedLog", "onDestroy: 停止播放");
            }
        }
//        釋放資源
        vv_player=null;
        media_controller=null;
        Log.d("SelfDefinedLog", "onDestroy: 將播放器與controller設為空");
    }
}