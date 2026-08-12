package com.example.application2;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.badge.BadgeUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSDecodeActivity extends AppCompatActivity {
    private TextView tv_lat, tv_lng, tv_result;
    private EditText et_lat, et_lng;
    private Button btn_show_address;
    private Geocoder geo;
    private List<Address> list_address;
    private double lat, lng;
    private static final int MAX_RESULT_LENGTH = 3;
    private String strOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpsdecode);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        tv_result = findViewById(R.id.tv_result_locName);
        et_lat = findViewById(R.id.et_lat);
        et_lng = findViewById(R.id.et_lng);
        btn_show_address = findViewById(R.id.btn_showLocationName);
        geo = new Geocoder(this, Locale.TAIWAN);

        btn_show_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, j;
                strOutput = "";
                tv_result.setText("查詢結果：");   //重置為預設字串

                String strLat = et_lat.getText().toString();
                String strLng = et_lng.getText().toString();
                if (strLat.isEmpty() || strLng.isEmpty()) {
                    tv_result.setText(tv_result.getText().toString() + "\n請先輸入座標！");
                    return;
                }
                try {
                    lat = Double.parseDouble(strLat);
                    lng = Double.parseDouble(strLng);
                } catch (NumberFormatException e){
                    tv_result.setText(tv_result.getText().toString() + "\n請輸入座標格式！");
                    return;
                }

                try {
                    list_address = geo.getFromLocation(lat, lng, MAX_RESULT_LENGTH);
                    if (list_address != null) {
                        if (!list_address.isEmpty()) {
                            Address tempAddress;
                            for (i = 0; i < list_address.size(); i++) {
                                tempAddress = list_address.get(i);
                                strOutput += "\n" + (i + 1) + "：";
                                for (j = 0; j <= tempAddress.getMaxAddressLineIndex(); j++) {
                                    strOutput += tempAddress.getAddressLine(j) + ",";
                                }
                            }
                        } else {
                            strOutput = "\n查詢結果為空(list_address.isEmpty())";
                        }
                    } else {
                        strOutput = "\n查詢結果為空(list_address == null)";
                    }
                } catch (IOException ex) {
                    strOutput = "\n非預期狀況\n" + ex.getMessage();
                }
                tv_result.setText(tv_result.getText().toString() + strOutput);

            }
        });

    }
}