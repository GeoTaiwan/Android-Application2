package com.example.application2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewActivity extends AppCompatActivity {

    TextView tv_title;
    ListView lv_main;
    SimpleAdapter simpleAdapterForListView;
    int i;
    String[] strColumns = {"item_menu", "item_price"};
    int[] intResourceID = {R.id.item_menu, R.id.item_price};


    String[] strMenu_item = {"赤肉麵線焿+脆皮炸雞", "五穀瘦肉粥+無骨雞塊", "鮮酥雞肉焿+黃金鮮酥雞", "五穀瘦肉粥+脆皮炸雞", "鮮脆雞腿堡+玉米濃湯", "鮮酥雞肉焿+烤醬雞堡"
            , "烤醬雞堡+脆皮炸雞", "香檸吉司豬排堡+脆皮炸雞", "赤肉麵線焿+鮮脆雞腿堡", "兩塊脆皮炸雞(腿、塊任選)", "香檸吉司豬排堡+無骨雞塊", "五穀瘦肉粥+黃金鮮酥雞", "赤肉麵線焿+豬肉可樂餅"
            , "香檸吉司豬排堡+香酥米糕"};
    String[] strItem_price = {"104", "97", "97", "104", "99", "103"
            , "107", "114", "114", "108", "107", "94", "82"
            , "99"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        lv_main = findViewById(R.id.lv_main);
        tv_title = findViewById(R.id.tv_listview_title);
        ArrayList<HashMap<String, String>> arrayList_itemDataSet = new ArrayList<>();
        HashMap<String, String> hasMap_itemData;
        for (i = 0; i < strMenu_item.length; i++) {
            hasMap_itemData = new HashMap<>();
            hasMap_itemData.put(strColumns[0],strMenu_item[i]);
            hasMap_itemData.put(strColumns[1],strItem_price[i]);
            arrayList_itemDataSet.add(hasMap_itemData);
        }

        simpleAdapterForListView = new SimpleAdapter(ListViewActivity.this,arrayList_itemDataSet,
                                                    R.layout.listview_item_layout, strColumns,intResourceID);

        lv_main.setAdapter(simpleAdapterForListView);

        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strItemMenu = strMenu_item[position];
                String strMenuPrice = strItem_price[position];
                String strResult = "選擇的餐點：「"+ strItemMenu + "」, 價格：" + strMenuPrice;

                Toast t = Toast.makeText(ListViewActivity.this,strResult, Toast.LENGTH_SHORT);
                t.show();
            }
        });

    }
}