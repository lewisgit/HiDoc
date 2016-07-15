package com.happydoc.lewis.myapplication.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.happydoc.lewis.myapplication.LoginActivity;
import com.happydoc.lewis.myapplication.PhoneVerifyActivity;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.account.LoginInfo;
import com.happydoc.lewis.myapplication.event.Event;
import com.happydoc.lewis.myapplication.event.EventListener;
import com.happydoc.lewis.myapplication.registerActivity;

/**
 * Created by Lewis on 2016/7/14.
 */
public class RegisterView extends MotherView{
    public Event<View, LoginInfo> event=new Event<>();
    public RegisterView(AppCompatActivity activity){
        super(activity);
    }
    public void whenOnClick(View v,EventListener<LoginInfo> listener){event.addEventListener(v, listener);}
    public void setBtnOnClickListener(){


        TextView backBtn=(TextView) activity.findViewById(R.id.back_reg);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.dispatch(v,null);
            }
        });
        Button registerBtn=(Button) activity.findViewById(R.id.register_rightaway);
        final EditText username=(EditText) activity.findViewById(R.id.register_username);
        final EditText pwd=(EditText) activity.findViewById(R.id.register_pwd);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginInfo loginInfo = new LoginInfo(username.getText().toString(), pwd.getText().toString());
                event.dispatch(v, loginInfo);
            }
        });

    }
    public void verifyPhone(String phoneNum){
        Intent intent=new Intent(activity,PhoneVerifyActivity.class);
        intent.putExtra("Phone", phoneNum);
        intent.putExtra("afterReg",true);
        activity.startActivity(intent);
        activity.finish();
    }
    public void goBackLogin(){
        Intent intent=new Intent(activity,LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
