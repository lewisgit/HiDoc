package com.happydoc.lewis.myapplication.account;

import com.avos.avoscloud.AVUser;

/**
 * Created by Lewis on 2016/7/15.
 */
public class CustomUserProvider {
    static public CustomUserProvider customUserProvider;
    public AVUser currentUser;
    public void setCurrentUser(AVUser user){currentUser=user;};
    public AVUser getCurrentUser(){return currentUser;};
    static public CustomUserProvider getInstance(){
        if(customUserProvider==null)
        {return new CustomUserProvider();} else{
            return customUserProvider;
        }
    }
}
