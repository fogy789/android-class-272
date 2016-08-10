package com.example.user.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    EditText ediText;
    RadioGroup radioGroup;
    ListView listview;

    String drink="Black Tea";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//指定使用activity_main這Layout的頁面

        textview = (TextView)findViewById(R.id.textView);
        ediText =(EditText) findViewById(R.id.editText);
        radioGroup=(RadioGroup)findViewById(R.id.rdg);
        listview=(ListView)findViewById(R.id.listView);

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

        setupListview();
    }


    private void setupListview()
    {
        String[] data =new String[]{"1","2","3","4","5","6","7","8"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listview.setAdapter(adapter);
    }

    public void click(View view)
    {
        String text = ediText.getText().toString();
        text = text + " Order " + drink;
        textview.setText(text);
        ediText.setText("");
    }
}
