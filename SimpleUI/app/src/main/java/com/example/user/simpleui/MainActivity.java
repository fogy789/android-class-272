package com.example.user.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    EditText ediText;
    RadioGroup radioGroup;

    String drink="Black Tea";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//指定使用activity_main這Layout的頁面

        textview = (TextView)findViewById(R.id.textView);
        ediText =(EditText) findViewById(R.id.editText);
        radioGroup=(RadioGroup)findViewById(R.id.rdg);


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
    }

    public void click(View view)
    {
        String text = ediText.getText().toString();
        text = text + " Order " + drink;
        textview.setText(text);
        ediText.setText("");
    }
}
