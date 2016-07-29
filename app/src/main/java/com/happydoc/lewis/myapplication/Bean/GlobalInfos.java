package com.happydoc.lewis.myapplication.Bean;

import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.happydoc.lewis.myapplication.fragmentinfo.FragmentInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lewis on 2016/7/23.
 */
public class GlobalInfos {
    static private UserInfo userInfo;
    static private DisplayImageOptions displayImageOptions;
    static private DoctorInfo curDocInfo;
    static private List<FragmentInfo> backStack;
    static private HashMap<String,FragmentInfo> fragmentList=new HashMap<>();
    static private List<Object> registerObj=new ArrayList<>();
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

    public static void addBackStack(FragmentInfo info) {
        if(GlobalInfos.backStack==null)
            GlobalInfos.backStack=new ArrayList<>();
        GlobalInfos.backStack.add(info);
    }

    public static List<FragmentInfo> getBackStack() {
        return backStack;
    }
    public static void popBackStack(){
        if(backStack.size()>0)
        backStack.remove(backStack.size()-1);
    }

    public static void clearBackStack() {
        if(backStack!=null)
        backStack.clear();
    }

    public static void setFragmentList(HashMap<String, FragmentInfo> fragmentList) {
        GlobalInfos.fragmentList = fragmentList;
    }

    public static HashMap<String, FragmentInfo> getFragmentList() {
        return fragmentList;
    }
    public static void addListRegObj(Object o){
        registerObj.add(o);
    }

    public static List<Object> getRegisterObj() {
        return registerObj;
    }
    public static void clearListRegObj(){
        registerObj.clear();
    }
}
