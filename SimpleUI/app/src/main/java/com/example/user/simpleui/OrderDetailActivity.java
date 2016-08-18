package com.example.user.simpleui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        TextView notetextview = (TextView)findViewById(R.id.notetextView);
        TextView storeInfotextview = (TextView)findViewById(R.id.storeinfotextView);
        TextView drinkOrderResultstextview = (TextView)findViewById(R.id.drinkorderresultstextView);
        TextView latlngtextview = (TextView)findViewById(R.id.latlngtextView);

        Intent intent = getIntent();
        Order order = intent.getParcelableExtra("order");
        notetextview.setText(order.getNote());
        storeInfotextview.setText(order.getStoreInfo());
        String resultText = "";
        for(DrinkOrder drinkOrder: order.getDrinkOrderList())
        {
            String lNumber = String.valueOf(drinkOrder.getLNumber());
            String mNumber = String.valueOf(drinkOrder.getmNumber());
            String drinkName = drinkOrder.getDrink().getName();
            resultText += drinkName + " M:" + mNumber + "  L:"+lNumber + "\n";
        }
        drinkOrderResultstextview.setText(resultText);
    }
}
