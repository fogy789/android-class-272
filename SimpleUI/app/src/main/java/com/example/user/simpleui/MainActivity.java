package com.example.user.simpleui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE_DRINK_MENU_ACTIVITY=0;

    TextView textview;
    EditText ediText;
    RadioGroup radioGroup;
    ListView listview;
    Spinner spinner;

    String drink="Black Tea";

    ArrayList<DrinkOrder> drinkOrderList = new ArrayList<>();
    List<Order> orderList = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//指定使用activity_main這Layout的頁面

        textview = (TextView)findViewById(R.id.textView);
        ediText =(EditText) findViewById(R.id.editText);
        radioGroup=(RadioGroup)findViewById(R.id.rdg);
        listview=(ListView)findViewById(R.id.listView);
        spinner=(Spinner)findViewById(R.id.spinner);

        sharedPreferences = getSharedPreferences("UIState", MODE_PRIVATE);//xml的檔名及模式
        editor = sharedPreferences.edit();//用editor寫檔

        ediText.setText(sharedPreferences.getString("ediText", ""));
        ediText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString("ediText", ediText.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.blackTea) {
                    drink = "Black Tea";
                } else if (checkedId == R.id.greenTea) {
                    drink = "Green Tea";
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

        setupOrderHistory();
        setupListview();
        setupSpinner();
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    Toast.makeText(MainActivity.this, "Sucess", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("TestObject");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(e == null)
//                {
//                    Toast.makeText(MainActivity.this, objects.get(0).getString("foo"), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        Log.d("DEBUG","MainActivity onCreate");
    }

    private void setupOrderHistory()
    {
//        String orderDatas = Utils.readFile(this, "history");
//        String[] orderDataArray = orderDatas.split("\n");
//        Gson gson = new Gson();
//        for(String orderData : orderDataArray)
//        {
//            try {
//                Order order = gson.fromJson(orderData, Order.class);
//                if (order != null) {
//                    orderList.add(order);
//                }
//            }
//            catch (JsonSyntaxException e)
//            {
//                e.printStackTrace();
//            }
//        }

    }

    private void setupSpinner()
    {
        String[] storeInfo = getResources().getStringArray(R.array.storeInfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,storeInfo);
        spinner.setAdapter(adapter);
        spinner.setSelection(sharedPreferences.getInt("spinnerSelection", 0));//放在與spinner相關的地方，這行是若一開始上未勾選就選第1個選項
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//這段式保留以勾選的選項
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("spinnerSelection", spinner.getSelectedItemPosition());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupListview()
    {
//        String[] orderList =new String[]{"1","2","3","4","5","6","7","8"};

//        List<Map<String, String>> mapList = new ArrayList<>();
//
//        for(Order order : orderList)
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

        OrderAdapter adapter = new OrderAdapter(this, orderList);
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
        order.drinkOrderList = drinkOrderList;
        order.storeInfo = (String)spinner.getSelectedItem();

        orderList.add(order);

        Gson gson = new Gson();
        String orderData = gson.toJson(order);
        Utils.writeFile(this, "history", orderData+"\n");
        //textview.setText(orderData);

        drinkOrderList = new ArrayList<>();//清空所有接收的// 飲料訂單

        setupListview();

    }

    public void goToMenu(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("result", drinkOrderList);
        intent.setClass(this, DrinkMenuActivity.class);
        startActivityForResult(intent,REQUEST_CODE_DRINK_MENU_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_DRINK_MENU_ACTIVITY)
        {
            if(resultCode == RESULT_OK)
            {
                drinkOrderList = data.getParcelableArrayListExtra("result");
                //Toast.makeText(this,result,Toast.LENGTH_LONG).show();
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this,"取消菜單",Toast.LENGTH_LONG).show();
            }
        }
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
