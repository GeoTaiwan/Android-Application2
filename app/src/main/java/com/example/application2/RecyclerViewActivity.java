package com.example.application2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewActivity extends AppCompatActivity {
    RecyclerView recyclerview_main;
    SwipeRefreshLayout swipeRefreshLayout_main;
    ArrayList<HashMap<String, String>> arrayList_meal;
    RecyclerViewMealAdapter mealAdapter;

    String[] strMeal_name = {"赤肉麵線焿+脆皮炸雞", "五穀瘦肉粥+無骨雞塊", "鮮酥雞肉焿+黃金鮮酥雞", "五穀瘦肉粥+脆皮炸雞", "鮮脆雞腿堡+玉米濃湯", "鮮酥雞肉焿+烤醬雞堡"
            , "烤醬雞堡+脆皮炸雞", "香檸吉司豬排堡+脆皮炸雞", "赤肉麵線焿+鮮脆雞腿堡", "兩塊脆皮炸雞(腿、塊任選)", "香檸吉司豬排堡+無骨雞塊", "五穀瘦肉粥+黃金鮮酥雞", "赤肉麵線焿+豬肉可樂餅"
            , "香檸吉司豬排堡+香酥米糕"};
    String[] strMeal_price = {"104", "97", "97", "104", "99", "103"
            , "107", "114", "114", "108", "107", "94", "82"
            , "99"};
    String[] columns = {"menu_item", "meal_name", "meal_price"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler_view);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        recyclerview_main = findViewById(R.id.rv_main);
        recyclerview_main.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
        swipeRefreshLayout_main = findViewById(R.id.swipeRefreshLayout);
        arrayList_meal =   new ArrayList<>();
        mealAdapter = new RecyclerViewMealAdapter(RecyclerViewActivity.this);

        prepareMealDataSet();

        mealAdapter.setMealArrayList(arrayList_meal);
        mealAdapter.setColumns(columns);
        recyclerview_main.setAdapter(mealAdapter);
        swipeRefreshLayout_main.setColorSchemeColors(0x0000ff);
        swipeRefreshLayout_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList_meal.clear();
                prepareMealDataSet();
                mealAdapter.notifyDataSetChanged();
                swipeRefreshLayout_main.setRefreshing(false);
            }
        });

    }



    public void prepareMealDataSet(){
        int i;

        //資料讀入arrayList_meal
        HashMap<String, String> tempHashMap;
        for (i = 0; i < strMeal_name.length; i++) {
            tempHashMap = new HashMap<>();
            tempHashMap.put(columns[0], (i+1) + "號餐");
            tempHashMap.put(columns[1], strMeal_name[i]);
            tempHashMap.put(columns[2], strMeal_price[i]);
            arrayList_meal.add(tempHashMap);
        }

    }
}