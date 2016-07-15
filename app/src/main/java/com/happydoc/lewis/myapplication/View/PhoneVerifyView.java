package com.happydoc.lewis.myapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.happydoc.lewis.myapplication.LoginActivity;
import com.happydoc.lewis.myapplication.MainActivity;
import com.happydoc.lewis.myapplication.PhoneVerifyActivity;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.account.VerifyInfo;
import com.happydoc.lewis.myapplication.event.Event;
import com.happydoc.lewis.myapplication.event.EventListener;
import com.happydoc.lewis.myapplication.pwdActivity;

/**
 * Created by Lewis on 2016/7/15.
 */
public class PhoneVerifyView extends MotherView {
    public Event<View,VerifyInfo> event=new Event<>();
    public PhoneVerifyView(AppCompatActivity activity){
        super(activity);
    }
    public String getPhoneNum(){
        Intent intent=activity.getIntent();
        return intent.getStringExtra("Phone");
    }
    public boolean getAfterReg(){
        Intent intent=activity.getIntent();
        return intent.getBooleanExtra("afterReg",false);
    }
    public void whenOnClick(View v,EventListener<VerifyInfo> listener){
        event.addEventListener(v,listener);
    }
    public void setBtnOnClickListener(){
        Button verifyBtn=(Button) activity.findViewById(R.id.verify_code_button);
        Button getAuthCodeBtn=(Button) activity.findViewById(R.id.get_code_button);
        final EditText authCode=(EditText) activity.findViewById(R.id.input_verifiedcode);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.dispatch(v,new VerifyInfo(getPhoneNum(),authCode.getText().toString()));
            }
        });
        getAuthCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.dispatch(v, new VerifyInfo(getPhoneNum(), null));
            }
        });

        TextView backBtn=(TextView) activity.findViewById(R.id.back_phone);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.dispatch(v,null);
            }
        });

    }
    public void goBackLogin(){
        Intent intent=new Intent(activity,LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public void goMainPage(){
        Intent intent=new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
