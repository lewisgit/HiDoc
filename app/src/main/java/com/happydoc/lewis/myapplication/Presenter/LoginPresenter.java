package com.happydoc.lewis.myapplication.Presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogInCallback;
import com.avoscloud.leanchatlib.activity.AVChatActivity;
import com.happydoc.lewis.myapplication.Model.LoginModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.LoginView;
import com.happydoc.lewis.myapplication.account.CustomUserProvider;
import com.happydoc.lewis.myapplication.account.LoginInfo;
import com.happydoc.lewis.myapplication.account.UserValidation;
import com.happydoc.lewis.myapplication.account.AccountCode;
import com.happydoc.lewis.myapplication.event.EventListener;

/**
 * Created by Lewis on 2016/7/14.
 */
public class LoginPresenter {
    public LoginView loginView;
    public LoginModel loginModel;
    private AVUser currentUser=new AVUser();
    public LoginPresenter(LoginView view, LoginModel model){
        this.loginModel=model;
        this.loginView=view;
        initialize();
    }
    //----------login phase button listener Start--------//
    private void initialize(){
        //---------------register btn setting-------------------//
        loginView.whenBtnOnClick(loginView.getView(R.id.register_now), new EventListener<LoginInfo>() {
            @Override
            public void onDispatch(LoginInfo data) {
                loginView.registerNow();
            }
        });
        //---------------forget btn setting--------------------//
        loginView.whenBtnOnClick(loginView.getView(R.id.forget_password), new EventListener<LoginInfo>() {
            @Override
            public void onDispatch(LoginInfo data) {
                loginView.forgetPwd();
            }
        });
        //---------------login btn setting---------------------//
        loginView.whenBtnOnClick(loginView.getView(R.id.btn_login), new EventListener<LoginInfo>() {
            @Override
            public void onDispatch(LoginInfo data) {
                if(!data.username.isEmpty()){
                    if(!data.pwd.isEmpty()){
                        currentUser.setUsername(data.username);
                        currentUser.setMobilePhoneNumber(data.username);
                        //CustomUserProvider.getInstance().setCurrentUser(currentUser);
                        loginModel.userLogin(data.username, data.pwd, new LogInCallback() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if(e==null)
                                {
                                    loginVerify(avUser);
                                }else{
                                    processLeanCloudErrCode(e.getCode());
                                }
                            }
                        });
                    }else{
                        loginView.showMsg(R.string.empty_pwd);
                    }
                }else{
                    loginView.showMsg(R.string.empty_username);
                }
            }
        });
        loginView.setButtonListener();
    }
    //--------------------END---------------------------//

    //-------verify whether the login account is valid
    public void loginVerify(AVUser avUser){
        if(avUser!=null){
            UserValidation userValidation=loginModel.isValidUser(avUser);
            if(userValidation.isValid){
                CustomUserProvider.getInstance().setCurrentUser(currentUser);
                loginView.loginSucess();
            }else{
                processErrCode(userValidation.errCode);
            }}
    }
    //----------Show Error Message-------------------//
    public void processErrCode(AccountCode code){
        switch (code){
            case inValidUser:
                loginView.showMsg(R.string.invalid_user);
                break;
            case isDocUser:
                AVUser.logOut();
                loginView.showMsg(R.string.is_doc_account);
                break;
            case isUsernotVerify:
                loginView.showMsg(R.string.not_phone_verify);
                loginView.phoneVerifyNeeded(currentUser.getMobilePhoneNumber());
                break;
        }
    }
    //----------------END----------------------------//
    public void processLeanCloudErrCode(int code){
        switch (code){
            case 210:
                loginView.showMsg(R.string.password_is_wrong);
                break;
            case 211:
                loginView.showMsg(R.string.account_not_exist);
                break;
            case 215:
                loginView.showMsg(R.string.not_phone_verify);
                loginView.phoneVerifyNeeded(currentUser.getMobilePhoneNumber());
                break;
            default:
                loginView.showMsg(R.string.network_error);
                break;
        }
    }
}
