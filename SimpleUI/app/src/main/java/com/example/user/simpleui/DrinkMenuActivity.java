package com.example.user.simpleui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DrinkMenuActivity extends AppCompatActivity implements DrinkOrderDialog.OnFragmentInteractionListener{

    ListView drinkMenuListView;
    TextView totalTextView;
    int total = 0;

    String[] names = {"冬瓜紅茶","玫瑰鹽奶蓋紅茶","珍珠紅茶拿鐵","紅茶拿鐵"};
    int[] lPrices = {35,45,55,45};
    int[] mPrices = {25,35,45,35};
    int[] images = {R.drawable.drink1,R.drawable.drink2,R.drawable.drink3,R.drawable.drink4};//給id

    List<Drink> drinkList = new ArrayList<>();
    ArrayList<DrinkOrder> drinkOrderList = new ArrayList<>();//把飲料訂單存下來

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);

        setData();

        Intent intent = getIntent();
        drinkOrderList = intent.getParcelableArrayListExtra("result");
        drinkMenuListView =(ListView) findViewById(R.id.drinkIdlistView);
        totalTextView = (TextView) findViewById(R.id.totaltextview);

        updateTotalTextView();
        drinkMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Drink drink = (Drink) parent.getAdapter().getItem(position);
//                total+=drink.mPrice;
//                totalTextView.setText(String.valueOf(total));
                showDrinkOrderDialog(drink);
            }
        });


//                setupDrinkMenu();

        Log.d("DEBUG", "DrinkMenuActivity");

    }

    private void setData()
    {
//        for (int i = 0 ; i < names.length ; i++ )
//        {
//            Drink drink = new Drink();
//            drink.name = names[i];
//            drink.mPrice = mPrices[i];
//            drink.lPrice = lPrices[i];
//            drink.imageId = images[i];
//            drinkList.add(drink);
//        }data後來放在網路上，所以就不用了
        Drink.getQuery().findInBackground(new FindCallback<Drink>() {
            @Override
            public void done(List<Drink> objects, ParseException e) {
                if(e == null)
                {
                    drinkList = objects;
                    setupDrinkMenu();
                }

            }
        });
    }

    public void done(View view)//onclick的指定格式
    {
        Intent intent = new Intent();
        intent.putExtra("result", drinkOrderList);

        setResult(RESULT_OK,intent);
        finish();
    }

    private void showDrinkOrderDialog(Drink drink)
    {
        DrinkOrder order = null;
        for(DrinkOrder drinkOrder : drinkOrderList)
        {
            if(drinkOrder.getDrink().getObjectId().equals(drink.getObjectId()))
            {
                order = drinkOrder;
                break;
            }
        }

        if(order == null)
            order = new DrinkOrder(drink);

        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();

        DrinkOrderDialog dialog = DrinkOrderDialog.newInstance(order);

//        ft.replace(R.id.root,dialog);
//
//        ft.commit();
        dialog.show(ft, "DrinkOrderDialog");
    }

    public void cancel(View view)
    {
        Intent intent = new Intent();

        setResult(RESULT_CANCELED,intent);
        finish();
    }

    private void setupDrinkMenu()
    {
        DrinkAdapter adapter = new DrinkAdapter(this, drinkList);
        drinkMenuListView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DEBUG", "DrinkMenuActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUG","DrinkMenuActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG", "DrinkMenuActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DEBUG", "DrinkMenuActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "DrinkMenuActivity onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("DEBUG", "DrinkMenuActivity onRestart");
    }

    @Override
    public void onDrinkOrderResult(DrinkOrder drinkOrder) {

        boolean flag = false;
        for(int i =0; i<drinkOrderList.size() ; i++)
        {
            if(drinkOrderList.get(i).getDrink().getObjectId().equals(drinkOrder.getDrink().getObjectId()))
            {
                drinkOrderList.set(i,drinkOrder);
                flag = true;
                break;
            }
        }

        if(!flag)
            drinkOrderList.add(drinkOrder);
        updateTotalTextView();

    }

    private void updateTotalTextView()
    {
        int total = 0;
        for (DrinkOrder drinkOrder : drinkOrderList)
        {
            total += drinkOrder.getLNumber()*drinkOrder.getDrink().getlPrice() + drinkOrder.getmNumber()*drinkOrder.getDrink().getmPrice();
        }

        totalTextView.setText(String.valueOf(total));
    }
}
