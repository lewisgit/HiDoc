package com.happydoc.lewis.myapplication.Model;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogInCallback;
import com.happydoc.lewis.myapplication.account.UserValidation;
import com.happydoc.lewis.myapplication.account.AccountCode;

/**
 * Created by Lewis on 2016/7/14.
 */
public class LoginModel {
    public AVUser getCurrentUser(){
        return AVUser.getCurrentUser();
    }
    public void getUpdatedUserData(AVUser user,GetCallback<AVObject> callBack){
        user.fetchInBackground(callBack);
    }
    //--------------------User Validation process---------------------------
    public UserValidation isValidUser(AVUser user){
        if(user!=null)
        {
            if(!user.getBoolean("isDocter")){//if null?
                if(user.isMobilePhoneVerified()){
                    return new UserValidation(true, AccountCode.validUser);
                }
                else{
                    return new UserValidation(false, AccountCode.isUsernotVerify);
                }
            }else {
                return new UserValidation(false, AccountCode.isDocUser);
            }
        }
        else{
            return new UserValidation(false, AccountCode.inValidUser);
        }
    }
    //----------------------------------------------------------------------
/*    public UserValidation isValidDoc(AVUser user){

    }*/
    public void userLogin(String username,String pwd,LogInCallback callback){
        AVUser.logInInBackground(username,pwd,callback);
    }
}
