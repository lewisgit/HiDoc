package com.happydoc.lewis.myapplication.account;

import java.security.PublicKey;

/**
 * Created by Lewis on 2016/7/15.
 */
public class ModifyPwdInfo {
    public String phoneNum;
    public String newPwd;
    public String authCode;
    public ModifyPwdInfo(String phoneNum,String newPwd,String authCode){
        this.phoneNum=phoneNum;
        this.newPwd=newPwd;
        this.authCode=authCode;
    }
}
