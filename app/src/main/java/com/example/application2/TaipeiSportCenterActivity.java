package com.example.application2;

import android.app.DownloadManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaipeiSportCenterActivity extends AppCompatActivity {

    TextView tv_main;
    Button btn_get_data, btn_clear_data;
    ListView lv_api_data;
    ArrayList<String> taipei_sport_center_arrListOfString;
    ArrayAdapter<String> taipei_sport_center_arrAdapter;
    String strAPI_URL = "https://data.taipei/api/v1/dataset/e7c46724-3517-4ce5-844f-5a4404897b7d?scope=resourceAquire";
    String strTitle = "臺北市運動中心資料";
    String strCenterName, strCenterAddress, strLat, strLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_taipei_sport_center);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        tv_main = findViewById(R.id.tv_taipei_sport_center_title);
        btn_get_data = findViewById(R.id.btn_get_data_tsc);
        btn_clear_data = findViewById(R.id.btn_clear_data_tsc);
        lv_api_data = findViewById(R.id.lv_taipei_sport_center);

        taipei_sport_center_arrListOfString = new ArrayList<String>();

        btn_clear_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((taipei_sport_center_arrListOfString != null) && (taipei_sport_center_arrAdapter != null)) {
                    taipei_sport_center_arrListOfString.clear();
                    taipei_sport_center_arrAdapter.notifyDataSetChanged();
                    tv_main.setText(strTitle + "\nAPI 資料已清空…");
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_main.setText(strTitle);
                        }
                    }, 5000);
                }
            }
        });

        btn_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler h = new Handler();
                try {
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strAPI_URL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            int i;
                            try {
                                JSONObject jsonObject_result = jsonObject.getJSONObject("result");
                                JSONArray jsonArray_results = jsonObject_result.getJSONArray("results");

                                for (i = 0; i < jsonArray_results.length(); i++) {
                                    JSONObject jsonObject_sport_center = jsonArray_results.getJSONObject(i);
                                    strCenterName = jsonObject_sport_center.getString("名稱");
                                    strCenterAddress = jsonObject_sport_center.getString("地址");
                                    strLat = jsonObject_sport_center.getString("緯度");
                                    strLng = jsonObject_sport_center.getString("經度");

                                    taipei_sport_center_arrListOfString.add((i+1)+". "+ strCenterName + "\n" + strCenterAddress
                                            + "\n座標：" + strLat + ", " + strLng);
                                }

                                taipei_sport_center_arrAdapter = new ArrayAdapter<String>(TaipeiSportCenterActivity.this,
                                        android.R.layout.simple_list_item_1,taipei_sport_center_arrListOfString);
                                lv_api_data.setAdapter(taipei_sport_center_arrAdapter);
                                tv_main.setText(strTitle + "\nAPI資料讀取完成，解析成功！！");
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_main.setText(strTitle);
                                    }
                                },5000);
                            } catch (JSONException ex) {
                                tv_main.setText(strTitle+"\nAPI資料解析失敗，錯誤訊息：\n" + ex.getMessage());

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            tv_main.setText(strTitle+"\n向伺服器取回API資料時發生 VolleyError，錯誤訊息：\n" + volleyError.getMessage());
                        }
                    });

                    Volley.newRequestQueue(TaipeiSportCenterActivity.this).add(request);
                    tv_main.setText(strTitle+ "\nAPI 資料正在讀取中…");
                } catch (Exception ex) {
                    tv_main.setText(strTitle + "\n嘗試向伺服器發送API請求失敗，錯誤訊息：\n" + ex.getMessage());
                }
            }
        });


    }
}