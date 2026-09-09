package com.example.application2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TaipeiSportCenterActivityOffline extends AppCompatActivity {

    TextView tv_main;
    Button btn_get_data, btn_clear_data;
    ListView lv_api_data;
    ArrayList<String> taipei_sport_center_arrListOfString;
    ArrayAdapter<String> taipei_sport_center_arrAdapter;
    String strAPI_URL = "https://data.taipei/api/v1/dataset/e7c46724-3517-4ce5-844f-5a4404897b7d?scope=resourceAquire";
    String strTitle = "臺北市運動中心資料";
    String strCenterName, strCenterAddress, strLat, strLng;

    private static final String fileName = "TaipeiSportCenterOfflineData.dat";
    private static final int MODE_ONLINE = 0;
    private static final int MODE_OFFLINE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.taipei_sport_center_offline_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;
        Toast t = new Toast(TaipeiSportCenterActivityOffline.this);
        id = item.getItemId();
        if (id == R.id.menu_item_clear_offline_data) {
            t.makeText(TaipeiSportCenterActivityOffline.this, "已清除儲存的離線資料", Toast.LENGTH_LONG).show();
            writeOfflineDataFile("");//清空離線資料
        } else if (id == R.id.menu_item_clear_list_data) {
            t.makeText(TaipeiSportCenterActivityOffline.this, "已按下清空顯示列表", Toast.LENGTH_LONG).show();
        } else if (id == R.id.menu_item_close_app) {
            t.makeText(TaipeiSportCenterActivityOffline.this, "已按下關閉App", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taipei_sport_center);

        tv_main = findViewById(R.id.tv_taipei_sport_center_title);
        btn_get_data = findViewById(R.id.btn_get_data_tsc);
        btn_clear_data = findViewById(R.id.btn_clear_data_tsc);
        lv_api_data = findViewById(R.id.lv_taipei_sport_center);

        taipei_sport_center_arrListOfString = new ArrayList<String>();

        btn_clear_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearArrayListAndListView();
                tv_main.setText(strTitle + "\nAPI 資料畫面已清空…");
                resetTextView(5000);
            }
        });

        btn_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearArrayListAndListView();
                try {
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strAPI_URL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObjectResponse) {
                            if (!retriveAndShowData(jsonObjectResponse, MODE_ONLINE)) {

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            processOfflineData();
                            tv_main.setText(strTitle + "\n向伺服器取回API資料時發生 VolleyError，錯誤訊息：\n" + volleyError.getMessage());
                            Toast.makeText(TaipeiSportCenterActivityOffline.this,
                                    "讀取API回傳資料時發生VolleyError，網路異常", Toast.LENGTH_LONG).show();

                        }
                    });

                    Volley.newRequestQueue(TaipeiSportCenterActivityOffline.this).add(request);
                    tv_main.setText(strTitle + "\nAPI 資料正在讀取中…");
                    Toast.makeText(TaipeiSportCenterActivityOffline.this,
                            "讀取中…", Toast.LENGTH_LONG).show();

                } catch (Exception ex) {
                    processOfflineData();
                    tv_main.setText(strTitle + "\n嘗試向伺服器發送API請求失敗，錯誤訊息：\n" + ex.getMessage());
                    Toast.makeText(TaipeiSportCenterActivityOffline.this,
                            "向WebAPI伺服器發送請求失敗，網路異常", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private boolean retriveAndShowData(JSONObject jsonObject, int mode) {
        int i;
        try {
            JSONObject jsonObject_result = jsonObject.getJSONObject("result");
            JSONArray jsonArray_results = jsonObject_result.getJSONArray("results");
            JSONObject jsonObject_sport_center = new JSONObject();

            for (i = 0; i < jsonArray_results.length(); i++) {
                jsonObject_sport_center = jsonArray_results.getJSONObject(i);
                strCenterName = jsonObject_sport_center.getString("名稱");
                strCenterAddress = jsonObject_sport_center.getString("地址");
                strLat = jsonObject_sport_center.getString("緯度");
                strLng = jsonObject_sport_center.getString("經度");

                taipei_sport_center_arrListOfString.add(jsonObject_sport_center.getInt("_id") + ". " +
                        strCenterName + "\n" + strCenterAddress
                        + "\n座標：" + strLat + ", " + strLng);
            }
            taipei_sport_center_arrAdapter = new ArrayAdapter<String>(TaipeiSportCenterActivityOffline.this,
                    android.R.layout.simple_list_item_1, taipei_sport_center_arrListOfString);
            lv_api_data.setAdapter(taipei_sport_center_arrAdapter);

            if (mode == MODE_ONLINE) {
                if (writeOfflineDataFile(jsonObject.toString())) {
                    Toast.makeText(TaipeiSportCenterActivityOffline.this, "離線資料已儲存！！！", Toast.LENGTH_LONG).show();
                }
            }

            tv_main.setText(strTitle + "\n" + (mode == MODE_OFFLINE ? "離線" : "API") + "資料讀取完成，解析成功！！" +
                    ((mode == MODE_OFFLINE) ? "離線模式" : "線上模式"));
            resetTextView(5000);

            lv_api_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String itemString = taipei_sport_center_arrListOfString.get(position);
                    int intStartOfGeo = itemString.indexOf("座標");
                    int intEndOfName = itemString.indexOf("\n");
                    String nameOfCenter = itemString.substring(0, intEndOfName);
                    String strGeoPosition = itemString.substring(intStartOfGeo + 3);
                    String strArrayLatLng[] = strGeoPosition.split(",");
                    String strLabel = strArrayLatLng[0] + "," + strArrayLatLng[1] + "(" + nameOfCenter + "位置)";
                    String strQuery = Uri.decode(strLabel);
                    Intent intentGeo = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + strGeoPosition +
                            "?q=" + strQuery + "z=32"));
                    startActivity(intentGeo);

                }
            });

            return true;
        } catch (JSONException ex) {
            tv_main.setText(strTitle + "\nAPI資料解析失敗，目前模式：" + (mode == MODE_OFFLINE ? "離線" : "連線") + "模式");
            return false;
        }
    }

    private void processOfflineData() {
        String strJSON;
        strJSON = readOfflineDataFile();
        if (!strJSON.isEmpty()) {
            try {
                tv_main.setText(strTitle + "\n離線資料讀取中…");
                Toast.makeText(TaipeiSportCenterActivityOffline.this,
                        "離線讀取中…", Toast.LENGTH_LONG).show();
                JSONObject jsonObject = new JSONObject(strJSON);
                retriveAndShowData(jsonObject,MODE_OFFLINE);
            }catch (JSONException e){
                tv_main.setText(strTitle + "\n離線資料讀取、解析失敗，錯誤：" + e.getMessage());
                Toast.makeText(TaipeiSportCenterActivityOffline.this,
                        "離線讀取失敗！", Toast.LENGTH_LONG).show();
            }
        } else {
            clearArrayListAndListView();
            tv_main.setText(strTitle+"\n無離線資料可顯示！！");
        }
    }

    private void clearArrayListAndListView() {
        if ((taipei_sport_center_arrListOfString != null) && (taipei_sport_center_arrAdapter != null)) {
            taipei_sport_center_arrListOfString.clear();
            taipei_sport_center_arrAdapter.notifyDataSetChanged();
        }
    }

    private void resetTextView(int millisecond) {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_main.setText(strTitle);
            }
        }, millisecond);
    }

    private boolean writeOfflineDataFile(String json) {
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    private String readOfflineDataFile(){
        String json, line;
        StringBuilder stringBuilder_json = new StringBuilder();
        try {
            FileInputStream fis = openFileInput(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder_json.append(line);
            }
            json = stringBuilder_json.toString();
            return json;
        } catch (FileNotFoundException e){
            tv_main.setText(strTitle+"\nAPI離線資料檔案不存在！");
        } catch (IOException e){
            tv_main.setText(strTitle+"\nAPI離線資料讀取失敗！");
        }
        return "";
    }

}