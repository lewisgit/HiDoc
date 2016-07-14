package com.happydoc.lewis.myapplication.account;

/**
 * Created by Lewis on 2016/7/14.
 */
public class UserValidation {
    public boolean isValid;
    public AccountCode errCode;
    public UserValidation(boolean isValid, AccountCode errCode){
        this.isValid=isValid;
        this.errCode=errCode;
    }
}
