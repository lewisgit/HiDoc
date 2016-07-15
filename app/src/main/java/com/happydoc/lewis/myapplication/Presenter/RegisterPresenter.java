package com.happydoc.lewis.myapplication.Presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.happydoc.lewis.myapplication.Model.RegisterModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.RegisterView;
import com.happydoc.lewis.myapplication.account.CustomUserProvider;
import com.happydoc.lewis.myapplication.account.LoginInfo;
import com.happydoc.lewis.myapplication.account.VerifyInfo;
import com.happydoc.lewis.myapplication.event.EventListener;

/**
 * Created by Lewis on 2016/7/14.
 */
public class RegisterPresenter {
    public RegisterView view;
    public RegisterModel model;
    public RegisterPresenter(RegisterView registerView,RegisterModel registerModel){
        this.view=registerView;
        this.model=registerModel;
        initialize();
    }
    public void initialize(){
        view.whenOnClick(view.getView(R.id.register_rightaway), new EventListener<LoginInfo>() {
            @Override
            public void onDispatch(LoginInfo data) {
                if (!data.username.isEmpty()) {
                    if (!data.pwd.isEmpty()) {
                        registerUser(data);
                    } else {
                        view.showMsg(R.string.empty_pwd);
                    }
                } else {
                    view.showMsg(R.string.empty_username);
                }
            }
        });
        view.whenOnClick(view.getView(R.id.back_reg), new EventListener<LoginInfo>() {
            @Override
            public void onDispatch(LoginInfo data) {
                view.goBackLogin();
            }
        });
        view.setBtnOnClickListener();
    }
    public void registerUser(LoginInfo loginInfo){
        final AVUser user=model.createUserAccount(loginInfo.username,loginInfo.pwd);
        model.registerUser(user, new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    view.showMsg(R.string.success_register_success);
                   // CustomUserProvider.getInstance().setCurrentUser(user);
                    view.verifyPhone(user.getMobilePhoneNumber());
                } else {
                    processLeanCloudErrCode(e.getCode());
                }
            }
        });
    }
    public void processLeanCloudErrCode(int code){
        switch (code){
            case 214:
                view.showMsg(R.string.error_register_user_name_repeat);
                break;
            case 601:
                view.showMsg(R.string.error_register_frequency_high);
                break;
            case 127:
                view.showMsg(R.string.invalid_phone);
                break;
            default:
                view.showMsg(R.string.network_error);
                break;
        }
    }
}
