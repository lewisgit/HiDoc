package com.happydoc.lewis.myapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.happydoc.lewis.myapplication.LoginActivity;
import com.happydoc.lewis.myapplication.PhoneVerifyActivity;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.account.ModifyPwdInfo;
import com.happydoc.lewis.myapplication.event.Event;
import com.happydoc.lewis.myapplication.event.EventListener;

/**
 * Created by Lewis on 2016/7/15.
 */
public class ForgetPwdView extends  MotherView{
    public Event<View,ModifyPwdInfo> event=new Event<>();
    public ForgetPwdView(AppCompatActivity activity){
        super(activity);
    }
    public void goBackLogin(){
        Intent intent=new Intent(activity,LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public void whenOnClick(View v,EventListener<ModifyPwdInfo> listener){
        event.addEventListener(v, listener);
    }
    public void setOnClickListener(){
        TextView backBtn=(TextView)activity.findViewById(R.id.back_pwd);
        Button sendSnsBtn=(Button) activity.findViewById(R.id.reset_get_code_button);
        Button modifyPwd=(Button) activity.findViewById(R.id.modify_pwd);
        final EditText phone=(EditText) activity.findViewById(R.id.modify_phone);
        final EditText newPwd=(EditText) activity.findViewById(R.id.modify_new_pwd);
        final EditText authCode=(EditText) activity.findViewById(R.id.modify_authcode);

        sendSnsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.dispatch(v,new ModifyPwdInfo(phone.getText().toString(),null,null));
            }
        });
        modifyPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.dispatch(v,new ModifyPwdInfo(phone.getText().toString(),newPwd.getText().toString(),authCode.getText().toString()));
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.dispatch(v,null);
            }
        });
    }
}
