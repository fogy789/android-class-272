package com.example.user.simpleui;

import android.app.Application;
import android.view.View;

import com.parse.Parse;

/**
 * Created by user on 2016/8/16.
 */
public class SympleUIApplication extends Application{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("khIdMP7yHMFraHrisclmzwthVkMSEj0o5O7Pwj8K")
                .server( "https://parseapi.back4app.com//")
                .clientKey("ALAAYg5tx3kKGhrMQv0PKrT7Oc9vM1h50dk4Iaz3")
        .build()
        );
    }
}