package com.example.user.simpleui;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.os.Handler;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        TextView notetextview = (TextView)findViewById(R.id.notetextView);
        TextView storeInfotextview = (TextView)findViewById(R.id.storeinfotextView);
        TextView drinkOrderResultstextview = (TextView)findViewById(R.id.drinkorderresultstextView);
        final TextView latlngtextview = (TextView)findViewById(R.id.latlngtextView);

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
//
//        final Handler handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//            latlngtextview.setText("123, 456");
//                return false;
//            }
//        });
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    handler.sendMessage(new Message());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();//thread.run()會把工作丟給mainthread增加其工作量，途中有睡眠，間若太長榮義一片黑,thread.start()是background睡
        (new GeoCodingTask()).execute("");
    }
}
