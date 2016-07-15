package com.happydoc.lewis.myapplication.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private Event<View,LoginInfo> event=new Event<>();
    public LoginView(AppCompatActivity base){
        super(base);
    }
    public void loginSucess(){
        Intent intent=new Intent(activity,MainActivity.class);
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
    public void forgetPwd(){
        Intent intent=new Intent(activity,pwdActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public void registerNow(){
        Intent intent=new Intent(activity,registerActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public void setButtonListener(){
        Button loginBtn=(Button) activity.findViewById(R.id.btn_login);
        TextView forgetPwdBtn=(TextView) activity.findViewById(R.id.forget_password);
        TextView registerBtn=(TextView) activity.findViewById(R.id.register_now);
        final EditText username=(EditText)activity.findViewById(R.id.login_username);
        final EditText pwd=(EditText)activity.findViewById(R.id.login_pwd);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginInfo loginInfo=new LoginInfo(username.getText().toString(),pwd.getText().toString());
                event.dispatch(v, loginInfo);
            }
        });
        forgetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.dispatch(v,null);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.dispatch(v,null);
            }
        });
    }
    public void whenBtnOnClick(View view,EventListener<LoginInfo> listener ){
        event.addEventListener(view, listener);
    }
/*    public View getBtn(int id){
        return  activity.findViewById(id);
    }*/
}
