package com.happydoc.lewis.myapplication.account;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avoscloud.leanchatlib.utils.ThirdPartUserUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lewis on 2016/7/15.
 */
public class CustomUserProvider implements  ThirdPartUserUtils.ThirdPartDataProvider{
    static public CustomUserProvider customUserProvider;
    public AVUser currentUser;
    public void setCurrentUser(AVUser user){currentUser=user;};
    public AVUser getCurrentUser(){return currentUser;};
    static public CustomUserProvider getInstance(){
        if(customUserProvider==null)
        {customUserProvider= new CustomUserProvider();}
            return customUserProvider;

    }
    public void getFriend(String userId, final ThirdPartUserUtils.FetchUserCallBack callBack){
        AVQuery<AVObject> query=new AVQuery<>("_User");
        query.whereEqualTo("objectId",userId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e==null){
                    List<ThirdPartUserUtils.ThirdPartUser> users=new ArrayList<>();
                    for(AVObject object:list)
                    {
                        String name=object.getString("Name");
                        name=name==null?"":name;
                        String objId=object.getObjectId();
                        AVFile file=object.getAVFile("Photo");
                        String url=file==null?"":file.getUrl();
                        ThirdPartUserUtils.ThirdPartUser user=new ThirdPartUserUtils.ThirdPartUser(objId,name,url);
                        users.add(user);
                    }
                    if(list.size()==0) callBack.done(null,new Exception("Get Friend Error"));
                    else callBack.done(users,null);
                }else{callBack.done(null,e);}
            }
        });
    }
    public void getFriends(List<String> list, final ThirdPartUserUtils.FetchUserCallBack callBack){
        AVQuery<AVObject> query=new AVQuery<>("_User");
        query.whereContainedIn("objectId",list);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e==null){
                    List<ThirdPartUserUtils.ThirdPartUser> users=new ArrayList<>();
                    for(AVObject object:list)
                    {
                        String name=object.getString("Name");
                        name=name==null?"":name;
                        String objId=object.getObjectId();
                        AVFile file=object.getAVFile("Photo");
                        String url=file==null?"":file.getUrl();
                        ThirdPartUserUtils.ThirdPartUser user=new ThirdPartUserUtils.ThirdPartUser(objId,name,url);
                        users.add(user);
                    }
                    callBack.done(users,null);
                }else{callBack.done(null,e);}
            }
        });
    }
    public void getFriends(int skip,int limit, final ThirdPartUserUtils.FetchUserCallBack callBack){
        AVQuery<AVObject> query=new AVQuery<>("_User");
        query.setLimit(limit);
        query.setSkip(skip);
       // query.whereContainedIn("objectId",list);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e==null){
                    List<ThirdPartUserUtils.ThirdPartUser> users=new ArrayList<>();
                    for(AVObject object:list)
                    {
                        String name=object.getString("Name");
                        name=name==null?"":name;
                        String objId=object.getObjectId();
                        AVFile file=object.getAVFile("Photo");
                        String url=file==null?"":file.getUrl();
                        ThirdPartUserUtils.ThirdPartUser user=new ThirdPartUserUtils.ThirdPartUser(objId,name,url);
                        users.add(user);
                    }
                    callBack.done(users,null);
                }else{callBack.done(null,e);}
            }
        });
    }
    public ThirdPartUserUtils.ThirdPartUser getSelf(){
        AVUser user=AVUser.getCurrentUser();
        if(user!=null){
            String name=user.getString("Name");
            name=name==null?"":name;
            String objId=user.getObjectId();
            AVFile file=user.getAVFile("Photo");
            String url=file==null?"":file.getUrl();
            return new ThirdPartUserUtils.ThirdPartUser(objId,name,url);
        }else{return new ThirdPartUserUtils.ThirdPartUser("","","");}
    }
}
