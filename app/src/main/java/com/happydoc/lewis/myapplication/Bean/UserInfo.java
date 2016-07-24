package com.happydoc.lewis.myapplication.Bean;

import com.avos.avoscloud.AVObject;

/**
 * Created by Lewis on 2016/7/23.
 */
public class UserInfo {
    AVObject userObj;
    String userName;
    String phoneNum;
    String userSex;
    String userCareer;
    String userAge;
    String userUrl;

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public void setUserObj(AVObject userObj) {
        this.userObj = userObj;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public void setUserCareer(String userCareer) {
        this.userCareer = userCareer;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public AVObject getUserObj() {
        return userObj;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserCareer() {
        return userCareer;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public String getUserUrl() {
        return userUrl;
    }
}
