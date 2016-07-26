package com.happydoc.lewis.myapplication.Model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.happydoc.lewis.myapplication.event.MyCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lewis on 2016/7/26.
 */
public class FollowModel {
    public void getMyFollow(final MyCallBack<List<DoctorInfo>> callBack){
        if(AVUser.getCurrentUser()!=null)
        {
            AVUser.getCurrentUser().getRelation("Followee").getQuery().findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if(e==null){
                        List<DoctorInfo> docList=new ArrayList<DoctorInfo>();
                        for(AVObject object:list)
                        {
                            docList.add(getDocInfo(object));
                        }
                        callBack.done(docList);
                    }else{callBack.done(null);}
                }
            });
        }else{
            callBack.done(null);
        }
    }
    public DoctorInfo getDocInfo(AVObject docItem){
        AVFile avatar=docItem.getAVFile("Photo");
        String name=docItem.getString("Name");
        String title=docItem.getString("Title");
        String hosp=docItem.getString("Hospital");
        String intro=docItem.getString("Intro");
        Number feeOnce=docItem.getNumber("feeOnce");
        Number feeWeek=docItem.getNumber("feeWeek");

        String avatarUrl=avatar==null?null:avatar.getUrl();
        name=name==null?"":name;
        title=title==null?"":title;
        hosp=hosp==null?"":hosp;
        intro=intro==null?"":intro;
        String feeOnceS=feeOnce==null?"":feeOnce.toString();
        String feeWeekS=feeWeek==null?"":feeWeek.toString();
        return new DoctorInfo(avatarUrl,name,title,hosp,intro,feeOnceS,feeWeekS,docItem);
    }
}
