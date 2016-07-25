package com.happydoc.lewis.myapplication.Bean;

import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Lewis on 2016/7/23.
 */
public class GlobalInfos {
    static private UserInfo userInfo;
    static private DisplayImageOptions displayImageOptions;
    static private DoctorInfo curDocInfo;
    public static void setDisplayImageOptions(DisplayImageOptions displayImageOptions) {
        GlobalInfos.displayImageOptions = displayImageOptions;
    }

    public static DisplayImageOptions getDisplayImageOptions() {
        return displayImageOptions;
    }

    public static void setUserInfo(UserInfo userInfo) {
        GlobalInfos.userInfo = userInfo;
    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setCurDocInfo(DoctorInfo curDocInfo) {
        GlobalInfos.curDocInfo = curDocInfo;
    }

    public static DoctorInfo getCurDocInfo() {
        return curDocInfo;
    }
}
