package com.happydoc.lewis.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVUser;
import com.happydoc.lewis.myapplication.Model.LoginModel;
import com.happydoc.lewis.myapplication.Presenter.LoginPresenter;
import com.happydoc.lewis.myapplication.Presenter.StartPresenter;
import com.happydoc.lewis.myapplication.View.LoginView;
import com.happydoc.lewis.myapplication.View.StartView;

/**
 * Created by Lewis on 2016/7/12.
 */
public class splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 100; //延迟三秒

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final StartView view=new StartView(this);
        final LoginModel model=new LoginModel();


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                new StartPresenter(view,model);
                /*Intent mainIntent = new Intent(splash.this, PhoneVerifyActivity.class);
                splash.this.startActivity(mainIntent);
                splash.this.finish();*/
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}