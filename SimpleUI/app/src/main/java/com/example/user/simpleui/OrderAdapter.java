package com.example.user.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
public class OrderAdapter extends BaseAdapter{

    List<Order> orders;
    LayoutInflater layoutInflater;

    public OrderAdapter(Context context,List<Order> orderList){
        this.orders = orderList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {//總共要顯示幾筆資料
        return orders.size();
    }

    @Override
    public Object getItem(int position) {//把第幾筆資料(position)丟給我
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {//第幾筆的ItemId丟給我，連上網路的時候就會希望拿到id在去找更進一步的資料
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//去copy getcount用for去拿每一筆的position

        Holder holder;

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.listview_order_items,null);

            TextView noteTextView = (TextView)convertView.findViewById(R.id.notetextview);
            TextView storeInfoTextView = (TextView)convertView.findViewById(R.id.storeInfotextView);
            TextView drinkTextView = (TextView)convertView.findViewById(R.id.drinktextView);

            holder = new Holder();

            holder.noteTextView = noteTextView;
            holder.storeInfoTextView = storeInfoTextView;
            holder.drinkTextView = drinkTextView;

            convertView.setTag(holder);
        }
        else
        {
            holder =(Holder) convertView.getTag();
        }

        Order order = orders.get(position);
        holder.noteTextView.setText(order.note);
        holder.storeInfoTextView.setText(order.storeInfo);
        holder.drinkTextView.setText(String.valueOf(order.getTotal()));

        return convertView;
    }

    class Holder{
        TextView noteTextView;
        TextView storeInfoTextView;
        TextView drinkTextView;
    }
}
