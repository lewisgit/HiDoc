package com.happydoc.lewis.myapplication.Presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogInCallback;
import com.happydoc.lewis.myapplication.Model.LoginModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.LoginView;
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
    private AVUser currentUser;
    public LoginPresenter(LoginView view, LoginModel model){
        this.loginModel=model;
        this.loginView=view;
        initialize();
    }
    //----------login phase button listener Start--------//
    private void initialize(){
        //---------------register btn setting-------------------//
        //---------------forget btn setting--------------------//
        //---------------login btn setting----------------------//
        loginView.whenBtnOnClick(loginView.getLoginBtn(), new EventListener<LoginInfo>() {
            @Override
            public void onDispatch(LoginInfo data) {
                if(!data.username.isEmpty()){
                    if(!data.pwd.isEmpty()){
                        loginModel.userLogin(data.username, data.pwd, new LogInCallback() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if(e==null)
                                {
                                    if(avUser!=null){
                                    UserValidation userValidation=loginModel.isValidUser(avUser);
                                    if(userValidation.isValid){
                                        loginView.loginSucess();
                                    }else{
                                        processErrCode(userValidation.errCode);
                                    }}
                                }
                          /*      else {
                                    switch (e.getCode()){
                                        case
                                    }
                                }*/
                                //!!!!!!!!SET ERROR PROCESS AND NULL USER CONDITION
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

    //----------Show Error Message-------------------//
    public void processErrCode(AccountCode code){
        switch (code){
            case inValidUser:
                loginView.showMsg(R.string.invalid_user);
                break;
            case isDocUser:
                loginView.showMsg(R.string.is_doc_account);
                break;
            case isUsernotVerify:
                loginView.showMsg(R.string.not_phone_verify);
                break;
        }
    }
    //----------------END----------------------------//
}
