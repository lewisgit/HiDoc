package com.happydoc.lewis.myapplication.Presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.happydoc.lewis.myapplication.Model.LoginModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.StartView;
import com.happydoc.lewis.myapplication.account.AccountCode;
import com.happydoc.lewis.myapplication.account.CustomUserProvider;
import com.happydoc.lewis.myapplication.account.UserValidation;

/**
 * Created by Lewis on 2016/7/15.
 */
public class StartPresenter {
    public StartView startView;
    public LoginModel loginModel;
    public AVUser currentUser;
    public StartPresenter(StartView startView,LoginModel loginModel){
        this.startView=startView;
        this.loginModel=loginModel;
        initialize();
    }
    public void initialize(){
        //CustomUserProvider.getInstance().setCurrentUser(loginModel.getCurrentUser());
        currentUser=loginModel.getCurrentUser();
        if(currentUser!=null){
            loginModel.getUpdatedUserData(currentUser, new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if (e == null) {
                        loginVerify((AVUser) avObject);
                    } else {
                        Log.d("Current User Sync Error", e.getMessage());
                        startView.loginSuccess();//when network error
                    }
                }
            });
        }else{
            Log.d("Login Error", "Null Current User");
            startView.loginNeeded();
        }
    }
    public void loginVerify(AVUser avUser){
        if (avUser == null) {
            startView.loginNeeded();
        } else {
            UserValidation validation=loginModel.isValidUser(avUser);
            if(validation.isValid){
                startView.loginSuccess();
            }else {
                processErrCode(validation.errCode);
            }
        }
    }
    public void processErrCode(AccountCode code){
        switch (code){
            case inValidUser:
                startView.showMsg(R.string.invalid_user);
                startView.loginNeeded();
                break;
            case isDocUser:
                AVUser.logOut();
                startView.showMsg(R.string.is_doc_account);
                startView.loginNeeded();
                break;
            case isUsernotVerify:
                startView.showMsg(R.string.not_phone_verify);
                startView.phoneVerifyNeeded(currentUser.getMobilePhoneNumber());
                break;
        }
    }
}
