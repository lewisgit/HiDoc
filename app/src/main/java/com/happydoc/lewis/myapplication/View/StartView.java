package com.happydoc.lewis.myapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.happydoc.lewis.myapplication.LoginActivity;
import com.happydoc.lewis.myapplication.MainActivity;
import com.happydoc.lewis.myapplication.PhoneVerifyActivity;

/**
 * Created by Lewis on 2016/7/15.
 */
public class StartView extends MotherView{
    //public AppCompatActivity activity;
    public StartView(AppCompatActivity activity){
        super(activity);
       // this.activity=activity;
    }
    public void loginNeeded(){
        Intent intent=new Intent(activity,LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public void phoneVerifyNeeded(String num){
        Intent intent=new Intent(activity,PhoneVerifyActivity.class);
        intent.putExtra("Phone",num);
        intent.putExtra("afterReg",false);
        activity.startActivity(intent);
        activity.finish();
    }
    public void loginSuccess(){
        Intent intent=new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
