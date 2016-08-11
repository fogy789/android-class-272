package com.example.user.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 2016/8/11.
 */
public class DrinkAdapter extends BaseAdapter{

    List<Drink> drinks;
    LayoutInflater layoutInflater;

    public DrinkAdapter(Context context, List<Drink> drinkList)
    {
        this.drinks = drinkList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return drinks.size();
    }

    @Override
    public Object getItem(int position) {
        return drinks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.listview_drink_menu_item,null);

            TextView drinknameTextView = (TextView)convertView.findViewById(R.id.drinkNametextView);
            TextView mPriceTextView = (TextView)convertView.findViewById(R.id.mPricetextView);
            TextView lPriceTextView = (TextView)convertView.findViewById(R.id.lPricetextView);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);

            holder = new Holder();

            holder.drinknameTextView = drinknameTextView;
            holder.lPriceTextView = lPriceTextView;
            holder.mPriceTextView = mPriceTextView;
            holder.imageView = imageView;

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        Drink drink = drinks.get(position);
        holder.drinknameTextView.setText(drink.name);
        holder.mPriceTextView.setText(String.valueOf(drink.mPrice));
        holder.lPriceTextView.setText(String.valueOf(drink.lPrice));
        holder.imageView.setImageResource(drink.imageId);

        return convertView;
    }

    class Holder{
        TextView drinknameTextView;
        TextView mPriceTextView;
        TextView lPriceTextView;
        ImageView imageView;
    }
}
