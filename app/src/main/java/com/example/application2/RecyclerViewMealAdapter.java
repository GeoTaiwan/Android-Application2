package com.example.application2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewMealAdapter extends RecyclerView.Adapter<RecyclerViewMealHolder> {

    private ArrayList<HashMap<String, String>> mealArrayList = new ArrayList<>();
    private Context mContext;

    private String[] columns = {};

    public RecyclerViewMealAdapter(Context context) {
        super();
        mContext = context;
    }


    @Override
    public RecyclerViewMealHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout, parent, false);
        RecyclerViewMealHolder itemMealHolder = new RecyclerViewMealHolder(v);
        itemMealHolder.item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = itemMealHolder.getBindingAdapterPosition();
                String strMenuItem = mealArrayList.get(position).get(getColumns()[0]);
                String strMealName = mealArrayList.get(position).get(getColumns()[1]);
                String strMealPrice = mealArrayList.get(position).get(getColumns()[2]);

                Toast t = Toast.makeText(v.getContext(),"您選擇的是：" + strMealName + "(" +
                                        strMenuItem + ")($" + strMealPrice + ")", Toast.LENGTH_SHORT);
                t.show();
            }
        });
        return itemMealHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerViewMealHolder holder, int position) {
        holder.tv_menu_item.setText(mealArrayList.get(position).get(getColumns()[0]));
        holder.tv_meal_name.setText(mealArrayList.get(position).get(getColumns()[1]));
        holder.tv_meal_price.setText(mealArrayList.get(position).get(getColumns()[2]));
    }

    @Override
    public int getItemCount() {
        if (mealArrayList == null) {
            return 0;
        } else {
            return mealArrayList.size();
        }
    }

    public void setMealArrayList(ArrayList<HashMap<String, String>> inputMealArrayList) {
        this.mealArrayList = inputMealArrayList;
        notifyDataSetChanged(); //通知RecyclerView資料變更
    }

    public ArrayList<HashMap<String, String>> getMealArrayList() {
        return mealArrayList;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] getColumns(){
        return this.columns;
    }
}
