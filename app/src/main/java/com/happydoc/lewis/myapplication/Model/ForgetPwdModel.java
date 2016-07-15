package com.happydoc.lewis.myapplication.Model;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.happydoc.lewis.myapplication.account.ModifyPwdInfo;

/**
 * Created by Lewis on 2016/7/15.
 */
public class ForgetPwdModel {
    public void requestAuthCode(String phoneNum, RequestMobileCodeCallback callback){
        AVUser.requestPasswordResetBySmsCodeInBackground(phoneNum,callback);
    }
    public void resetPwd(ModifyPwdInfo info,UpdatePasswordCallback callback){
        AVUser.resetPasswordBySmsCodeInBackground(info.authCode,info.newPwd,callback);
    }
}
