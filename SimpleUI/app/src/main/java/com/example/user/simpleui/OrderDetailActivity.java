package com.example.user.simpleui;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.os.Handler;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OrderDetailActivity extends AppCompatActivity implements GeoCodingTask.GeocodingCallback{

    TextView latlngTextView;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        TextView notetextview = (TextView)findViewById(R.id.notetextView);
        TextView storeInfotextview = (TextView)findViewById(R.id.storeinfotextView);
        TextView drinkOrderResultstextview = (TextView)findViewById(R.id.drinkorderresultstextView);
        latlngTextView = (TextView)findViewById(R.id.latlngtextView);

        Intent intent = getIntent();
        Order order = intent.getParcelableExtra("order");
        notetextview.setText(order.getNote());
        storeInfotextview.setText(order.getStoreInfo());
      final String address = order.getStoreInfo().split(",")[1];
        String resultText = "";
        for(DrinkOrder drinkOrder: order.getDrinkOrderList())
        {
            String lNumber = String.valueOf(drinkOrder.getLNumber());
            String mNumber = String.valueOf(drinkOrder.getmNumber());
            String drinkName = drinkOrder.getDrink().getName();
            resultText += drinkName + " M:" + mNumber + "  L:"+lNumber + "\n";
        }
        drinkOrderResultstextview.setText(resultText);

        MapFragment fragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                (new GeoCodingTask(OrderDetailActivity.this)).execute(address);
            }
        });
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

    }

    @Override
    public void done(double[] latlng) {
        if(latlng != null)
        {
            String latlngString = String.valueOf(latlng[0]+","+latlng[1]);
            latlngTextView.setText(latlngString);

            LatLng latLng = new LatLng(latlng[0], latlng[1]);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Smile");

            map.moveCamera(cameraUpdate);
            map.addMarker(markerOptions);
        }
    }
}
