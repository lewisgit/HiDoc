package com.happydoc.lewis.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.happydoc.lewis.myapplication.atts.timerCount;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lewis on 2016/7/12.
 */
public class PhoneVerifyActivity extends AppCompatActivity{

    @Bind(R.id.get_code_button)
    Button getCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        ButterKnife.bind(this);
        timerCount timer=new timerCount(getCodeButton);
        timer.start();
    }
    @OnClick(R.id.get_code_button)
    public void onGetCodeClick(View view){
        timerCount timer=new timerCount((Button) view);
        timer.start();
    }



}
