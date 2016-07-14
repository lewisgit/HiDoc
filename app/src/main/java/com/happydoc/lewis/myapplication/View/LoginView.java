package com.happydoc.lewis.myapplication.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.happydoc.lewis.myapplication.LoginActivity;
import com.happydoc.lewis.myapplication.MainActivity;
import com.happydoc.lewis.myapplication.PhoneVerifyActivity;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.account.LoginInfo;
import com.happydoc.lewis.myapplication.event.Event;
import com.happydoc.lewis.myapplication.event.EventListener;
import com.happydoc.lewis.myapplication.pwdActivity;
import com.happydoc.lewis.myapplication.registerActivity;

import butterknife.Bind;

/**
 * Created by Lewis on 2016/7/14.
 */
public class LoginView extends MotherView{
    //private AppCompatActivity activity;
    private Event<Button,LoginInfo> event=new Event<>();
    public LoginView(AppCompatActivity base){
        super(base);
    }
    public void loginSucess(){
        Intent intent=new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public void phoneVerifyNeeded(){
        Intent intent=new Intent(activity,PhoneVerifyActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public void forgetPwd(){
        Intent intent=new Intent(activity,pwdActivity.class);
        activity.startActivity(intent);
        //activity.finish();
    }
    public void registerNow(){
        Intent intent=new Intent(activity,registerActivity.class);
        activity.startActivity(intent);
        //activity.finish();
    }
    public void setButtonListener(){
        Button loginBtn=(Button) activity.findViewById(R.id.btn_login);
        final EditText username=(EditText)activity.findViewById(R.id.login_username);
        final EditText pwd=(EditText)activity.findViewById(R.id.login_pwd);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginInfo loginInfo=new LoginInfo(username.getText().toString(),pwd.getText().toString());
                event.dispatch((Button) v, loginInfo);
            }
        });
    }
    public void whenBtnOnClick(Button btn,EventListener<LoginInfo> listener ){
        event.addEventListener(btn, listener);
    }
    public Button getLoginBtn(){
        return (Button) activity.findViewById(R.id.btn_login);
    }
}
