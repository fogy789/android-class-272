package com.example.user.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    EditText ediText;
    RadioGroup radioGroup;
    ListView listview;
    Spinner spinner;

    String drink="Black Tea";

    List<Order> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//指定使用activity_main這Layout的頁面

        textview = (TextView)findViewById(R.id.textView);
        ediText =(EditText) findViewById(R.id.editText);
        radioGroup=(RadioGroup)findViewById(R.id.rdg);
        listview=(ListView)findViewById(R.id.listView);
        spinner=(Spinner)findViewById(R.id.spinner);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.blackTea)
                {
                    drink="Black Tea";
                }
                else if(checkedId==R.id.greenTea)
                {
                    drink="Green Tea";
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order) parent.getAdapter().getItem(position);//因為事物件，所以要轉型成Order
                Toast.makeText(MainActivity.this, order.note, Toast.LENGTH_LONG).show();//若被包在listener內的話，'this會變成只向linstener，要MainActivity.this才會指向MainActivity
            }
        });

        setupListview();
        setupSpinner();

        Log.d("DEBUG","MainActivity onCreate");
    }

    private void setupSpinner()
    {
        String[] storeInfo = getResources().getStringArray(R.array.storeInfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,storeInfo);
        spinner.setAdapter(adapter);
    }

    private void setupListview()
    {
//        String[] data =new String[]{"1","2","3","4","5","6","7","8"};

//        List<Map<String, String>> mapList = new ArrayList<>();
//
//        for(Order order : data)
//        {
//            Map<String ,String> item = new HashMap<>();
//
//            item.put("note",order.note);
//            item.put("storeInfo",order.storeInfo);
//            item.put("drink",order.drink);
//
//            mapList.add(item);
//        }
//
//        String[] from = {"note","storeInfo","drink"};
//        int[] to = {R.id.notetextview,R.id.storeInfotextView,R.id.drinktextView};
//
//        SimpleAdapter adapter = new SimpleAdapter(this, mapList, R.layout.listview_order_items, from, to);

        OrderAdapter adapter = new OrderAdapter(this, data);
        listview.setAdapter(adapter);
    }

    public void click(View view)
    {
        String text = ediText.getText().toString();
       String result = text + " Order " + drink;
        textview.setText(result);
        ediText.setText("");

        Order order = new Order();
        order.note = text;
        order.drink = drink;
        order.storeInfo = (String)spinner.getSelectedItem();
        data.add(order);
        setupListview();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DEBUG","MainActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUG","MainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG", "MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DEBUG", "MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "MainActivity onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("DEBUG", "MainActivity onRestart");
    }
}
