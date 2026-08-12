package com.example.application2;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSAddressActivity extends AppCompatActivity {

    TextView tv_result;
    EditText et_address;
    Button btn_query;
    String strAddressName, strOutput;
    Geocoder geo;
    List<Address> list_lat_lng;
    double lat, lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpsaddress);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        tv_result = findViewById(R.id.tv_result_lat_lng);
        et_address = findViewById(R.id.et_address);
        btn_query = findViewById(R.id.btn_query);

        geo = new Geocoder(GPSAddressActivity.this, Locale.TAIWAN);

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAddressName = et_address.getText().toString();

                try {
                    list_lat_lng = geo.getFromLocationName(strAddressName, 1);
                    if (list_lat_lng != null) {
                        if (!list_lat_lng.isEmpty()) {
                            //若list_lat_lng不為null
                            Address tempAddress = list_lat_lng.get(0);
                            lat = tempAddress.getLatitude();
                            lng = tempAddress.getLongitude();
                            strOutput = "\n緯度：" + lat +
                                    "\n經度：" + lng;
                        } else {
                            strOutput="\n回傳的 list_lat_lng 內容為空值，" +
                                    "\n輸入的地址、地名找不到符合的經緯度座標";
                        }
                    } else {
                        strOutput="\n回傳的 list_lat_lng = null，" +
                                "\n輸入的地址、地名找不到符合的經緯度座標";
                    }
                } catch (IOException ex) {
                    strOutput = "\n發生例外狀況：\n" + ex.getMessage();
                }
                tv_result.setText(tv_result.getText().toString().substring(0,6) + strOutput);
            }
        });

    }
}