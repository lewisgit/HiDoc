package com.happydoc.lewis.myapplication.Presenter;

import android.util.Log;
import android.widget.Button;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.happydoc.lewis.myapplication.Model.PhoneVerifyModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.PhoneVerifyView;
import com.happydoc.lewis.myapplication.account.CustomUserProvider;
import com.happydoc.lewis.myapplication.account.VerifyInfo;
import com.happydoc.lewis.myapplication.atts.TimerCount;
import com.happydoc.lewis.myapplication.event.EventListener;

/**
 * Created by Lewis on 2016/7/15.
 */
public class PhoneVerifyPresenter {
    public PhoneVerifyModel model;
    public PhoneVerifyView view;
    public PhoneVerifyPresenter(PhoneVerifyView view,PhoneVerifyModel model){
        this.model=model;
        this.view=view;
        initialize();
    }
    public void initialize(){
        //---count down----
        if(view.getAfterReg()){
            TimerCount timerCount=new TimerCount((Button)view.getView(R.id.get_code_button));
            timerCount.start();
        }
       // TimerCount timerCount=new TimerCount((Button)view.getView(R.id.get_code_button));
       // timerCount.start();
       // final String num=view.getPhoneNum();
        view.whenOnClick(view.getView(R.id.get_code_button), new EventListener<VerifyInfo>() {
            @Override
            public void onDispatch(VerifyInfo data) {
                if(data.phoneNum==null){
                    view.showMsg(R.string.phone_num_get_fail);
                }else{
                    model.getVerifyCode(data.phoneNum, new RequestMobileCodeCallback() {
                        @Override
                        public void done(AVException e) {
                            if(e==null){
                                view.showMsg(R.string.verify_request_sent);
                                TimerCount timerCount=new TimerCount((Button)view.getView(R.id.get_code_button));
                                timerCount.start();
                            }
                            else {
                                view.showMsg(e.getMessage());
                            }
                        }
                    });
                }
            }
        });

        view.whenOnClick(view.getView(R.id.verify_code_button), new EventListener<VerifyInfo>() {
            @Override
            public void onDispatch(VerifyInfo data) {
               if(data.authNum==null){
                   view.showMsg(R.string.input_verifiedcode);
               }else{
                   model.verifyPhone(data.authNum, new AVMobilePhoneVerifyCallback() {
                       @Override
                       public void done(AVException e) {
                           if (e == null) {
                               view.showMsg(R.string.verifySucceed);
                               afterVerify();
                           } else {
                               view.showMsg(R.string.verifyFailed);
                           }
                       }
                   });
               }
            }
        });
        view.whenOnClick(view.getView(R.id.back_phone), new EventListener<VerifyInfo>() {
            @Override
            public void onDispatch(VerifyInfo data) {
                view.goBackLogin();
            }
        });
        view.setBtnOnClickListener();
    }
    public void afterVerify(){
        AVUser user=AVUser.getCurrentUser();
        if(user==null){
            view.goBackLogin();
        }else{
            AVUser.getCurrentUser().put("mobilePhoneVerified", true);
            view.goMainPage();
        }
    }
}
