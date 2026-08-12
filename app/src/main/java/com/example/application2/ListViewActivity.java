package com.example.application2;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListViewActivity extends AppCompatActivity {

    TextView tv_title;
    ListView lv_main;
    SimpleAdapter simpleAdapterForListView;
    int i;
    String[] strTitle = {"menu","price"};
//    int[] intResourceID = {R.id.menu};


    String[] strMenu_item = {"赤肉麵線焿+脆皮炸雞","五穀瘦肉粥+無骨雞塊","鮮酥雞肉焿+黃金鮮酥雞","五穀瘦肉粥+脆皮炸雞","鮮脆雞腿堡+玉米濃湯","鮮酥雞肉焿+烤醬雞堡"
    ,"烤醬雞堡+脆皮炸雞","香檸吉司豬排堡+脆皮炸雞","赤肉麵線焿+鮮脆雞腿堡","兩塊脆皮炸雞(腿、塊任選)","香檸吉司豬排堡+無骨雞塊","五穀瘦肉粥+黃金鮮酥雞","赤肉麵線焿+豬肉可樂餅"
    ,"香檸吉司豬排堡+香酥米糕"};
    String[] strItem_price = {"104","97","97","104","99","103"
            ,"107","114","114","108","107","94","82"
            ,"99"};

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
        tv_title = findViewById(R.id.tv_title);



    }
}