package com.happydoc.lewis.myapplication.Model;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

/**
 * Created by Lewis on 2016/7/14.
 */
public class RegisterModel {
    public AVUser createUserAccount(String username,String pwd){
        AVUser user=new AVUser();
        user.setUsername(username);
        user.setPassword(pwd);
        user.setMobilePhoneNumber(username);
        user.put("isDoctor",false);
        return user;
    }
    public void registerUser(AVUser user,SignUpCallback signUpCallback){
        user.signUpInBackground(signUpCallback);
    }
}
