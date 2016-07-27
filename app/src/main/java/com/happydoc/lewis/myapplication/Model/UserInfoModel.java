package com.happydoc.lewis.myapplication.Model;

import android.net.Uri;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.happydoc.lewis.myapplication.Bean.UserInfo;
import com.happydoc.lewis.myapplication.event.MyCallBack;

import java.io.File;

/**
 * Created by Lewis on 2016/7/23.
 */
public class UserInfoModel {
    public void getUserInfo(final MyCallBack<UserInfo> callBack){
        AVUser currentUser=AVUser.getCurrentUser();
        if(currentUser!=null){
            currentUser.fetchIfNeededInBackground(new GetCallback<AVObject>() {
                @Override
                public void done(AVObject object, AVException e) {
                    if(e==null){
                        AVUser user=(AVUser)object;
                        UserInfo userInfo=new UserInfo();
                        userInfo.setUserObj(object);
                        String phone=user.getMobilePhoneNumber();
                        userInfo.setPhoneNum(phone);
                        String name=user.getString("Name");
                        userInfo.setUserName(name);
                        String sex=user.getString("Sex");
                        userInfo.setUserSex(sex);
                        String career=user.getString("Occupation");
                        userInfo.setUserCareer(career);
                        Number age=user.getNumber("Age");
                        String sAge=age==null?"":age.toString();
                        userInfo.setUserAge(sAge);
                        AVFile file=user.getAVFile("Photo");
                        String url=file==null?null:file.getUrl();
                        userInfo.setUserUrl(url);
                        callBack.done(userInfo);
                    }else{
                        callBack.done(null);
                    }
                }
            });
        }else{callBack.done(null);}
    }

    public void editName(String name,final MyCallBack<Exception> callBack){
        AVUser curUser=AVUser.getCurrentUser();
        if (curUser != null) {
            curUser.put("Name",name);
            curUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    callBack.done(e);
                }
            });
        }else{callBack.done(new Exception("edit fail!"));}
    }
    public void editSex(String sex, final MyCallBack<Exception> callBack){
        AVUser curUser=AVUser.getCurrentUser();
        if (curUser != null) {
            curUser.put("Sex",sex);
            curUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    callBack.done(e);
                }
            });
        }else{callBack.done(new Exception("edit fail!"));}
    }
    public void editAge(String age, final MyCallBack<Exception> callBack){
        AVUser curUser=AVUser.getCurrentUser();
        if (curUser != null) {
            curUser.put("Age",Integer.parseInt(age));
            curUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    callBack.done(e);
                }
            });
        }else{callBack.done(new Exception("edit fail!"));}
    }
    public void editCareer(String career, final MyCallBack<Exception> callBack){
        AVUser curUser=AVUser.getCurrentUser();
        if (curUser != null) {
            curUser.put("Occupation",career);
            curUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    callBack.done(e);
                }
            });
        }else{callBack.done(new Exception("edit fail!"));}
    }
    public void editImg(Uri uri,final MyCallBack<Exception> callBack){
        AVUser curUser=AVUser.getCurrentUser();
        if (curUser != null) {
            File file=new File(uri.toString());

        }
    }
}
