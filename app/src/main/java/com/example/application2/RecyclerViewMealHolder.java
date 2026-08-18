package com.example.application2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewMealHolder extends RecyclerView.ViewHolder{

    TextView tv_menu_item,tv_meal_name,tv_meal_price;
    View item_view;
    public RecyclerViewMealHolder(View itemView){
        super(itemView);
        tv_menu_item = itemView.findViewById(R.id.tv_menu_item);
        tv_meal_name = itemView.findViewById(R.id.tv_meal_name);
        tv_meal_price = itemView.findViewById(R.id.tv_meal_price);
        item_view = itemView;


    }
}
