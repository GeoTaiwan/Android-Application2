package com.example.application2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.health.connect.datatypes.ExerciseRoute;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class GPSActivity extends AppCompatActivity implements LocationListener {
    private TextView tv_gps_info;
    private Button btn_set_gps, btn_start_gps, btn_show_map;
    private LocationManager lm;
    private Location currentLocation;
    private String bestProvider;
    private static final int PERMISSION_REQUEST_GPS = 1201;

    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = "SD GPSActivity";
        Log.d(TAG, "onCreate: 開始");
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpsactivity);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        tv_gps_info = findViewById(R.id.tv_gps_information);
        btn_set_gps = findViewById(R.id.btn_set_gps);
        btn_start_gps = findViewById(R.id.btn_start_gps);
        btn_show_map = findViewById(R.id.btn_show_map);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        Log.d(TAG, "onCreate: 呼叫checkGPSProviderStatus()");
        checkGPSProviderStatus(lm);
        Log.d(TAG, "onCreate: 呼叫checkSDKVersion()");
        checkSDKVersion();

        Log.d(TAG, "onCreate: btn_set_gps onClickListener建立");
        btn_set_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        });

        Log.d(TAG, "onCreate: btn_start_gps onClickListener建立");
        btn_start_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat, lng;
                String strOutput;
                int minFlashTime = 1000;
                float minFlashDistance = 1.0f;

                bestProvider = "gps";

                try {
                    if (bestProvider != null) {
                        lm.requestLocationUpdates(bestProvider, minFlashTime, minFlashDistance, GPSActivity.this);
                        currentLocation = lm.getLastKnownLocation(bestProvider);
                        if (currentLocation != null) {
                            lat = currentLocation.getLatitude();
                            lng = currentLocation.getLongitude();
                            strOutput = "GPS資訊：啟動 GPS 座標更新功能成功！！\n定位資料提供者：" + bestProvider +
                                    "\n目前座標，緯度：" + lat + " - 經度：" + lng;
                        } else {
                            strOutput = "GPS資訊：等待GPS座標更新中… 目前座標 = null";
                        }
                    } else {
                        strOutput = "GPS資訊：啟動 GPS 座標更新功能失敗…… 定位資料提供者 = null";
                    }
                    tv_gps_info.setText(strOutput);
                } catch (SecurityException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btn_show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat, lng;
                String label = "目前位置";
                String geoString, labelString, uriString;
                if (currentLocation != null) {
                    lat = currentLocation.getLatitude();
                    lng = currentLocation.getLongitude();
                    geoString = "geo:" + lat + "," + lng;
                    labelString = lat + "," + lng + "(" + label + ")";
                    uriString = Uri.encode(labelString);
                    geoString = geoString + "?q=" + uriString;
                    Intent geoIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(geoString));
                    startActivity(geoIntent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GPSActivity.this);
                    builder.setTitle("GPS 地圖功能");
                    builder.setMessage("系統目前GPS位置(currentLocation)為NULL，請先啟用GPS更新功能，否則無法正確顯示地圖及座標位置…");
                    builder.setPositiveButton("確認",null);
                    builder.create().show();
                }
            }
        });

        Log.d(TAG, "onCreate: 結束");
    }

    public void checkGPSProviderStatus(LocationManager location_Manager) {
        Log.d(TAG, "checkGPSProviderStatus: 檢查GPS狀態");
        if (location_Manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
            Log.d(TAG, "checkGPSProviderStatus: GPS未啟用");
            AlertDialog.Builder builder = new AlertDialog.Builder(GPSActivity.this);
            builder.setTitle("GPS 硬體功能設定");
            builder.setMessage("GPS 硬體裝置尚未啟用！！\n請先啟用 GPS 裝置，否則無法接收位置資訊……");
            builder.setPositiveButton("確認", null);
            builder.create();
            builder.show();
        } else {
            Log.d(TAG, "checkGPSProviderStatus: GPS已啟用");
            Toast.makeText(GPSActivity.this, "GPS 硬體裝置已啟用", Toast.LENGTH_LONG).show();
        }
    }

    public void checkSDKVersion() {
        Log.d(TAG, "checkSDKVersion: 檢查SDK版本");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "checkSDKVersion: 版本大於Android 6(API23)");
                AlertDialog.Builder builder = new AlertDialog.Builder(GPSActivity.this);
                builder.setTitle("GPS 硬體存取權限檢查");
                builder.setMessage("SDK版查大於 Android 6(API 23)，需進行二次授權！！");
                builder.setPositiveButton("確認", null);
                builder.create().show();
                Log.d(TAG, "checkSDKVersion: 要求授權");
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_GPS);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //123
        double lat, lng;
        String strOutput;

        if (location != null) {
            currentLocation = location;
            lat = currentLocation.getLatitude();
            lng = currentLocation.getLongitude();
            strOutput = "GPS資訊：onLocationChanged 座標更新成功！！\n定位資料提供者：" + bestProvider +
                    "\n目前座標，緯度：" + lat + " - 經度：" + lng;
        } else {
            strOutput = "GPS資訊：onLocationChanged 座標更新失敗… onLocationChanged -> location 為 null";
        }
        tv_gps_info.setText(strOutput);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String strOutput;
        if (requestCode == PERMISSION_REQUEST_GPS) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    strOutput = "GPS資訊：要求 GPS 硬體二次授權成功！！";
                } else {
                    strOutput = "GPS資訊：要求 GPS 硬體二次授權失敗…";
                }
            } else {
                strOutput = "GPS資訊：系統錯誤！！ 未成功回傳GPS裝置授權要求…";
            }
            tv_gps_info.setText(strOutput);
        }
    }


}