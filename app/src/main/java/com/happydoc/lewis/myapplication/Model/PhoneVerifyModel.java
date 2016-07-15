package com.happydoc.lewis.myapplication.Model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;

/**
 * Created by Lewis on 2016/7/15.
 */
public class PhoneVerifyModel {
    public void getVerifyCode(String num,RequestMobileCodeCallback callback){
        AVUser.requestMobilePhoneVerifyInBackground(num, callback);
    }
    public void verifyPhone(String authCode,AVMobilePhoneVerifyCallback callback){
        AVUser.verifyMobilePhoneInBackground(authCode,callback);
    }
}
