package com.happydoc.lewis.myapplication.Model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.happydoc.lewis.myapplication.Bean.UserInfo;
import com.happydoc.lewis.myapplication.event.MyCallBack;

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
                        String age=user.getString("Age");
                        userInfo.setUserAge(age);
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
}
