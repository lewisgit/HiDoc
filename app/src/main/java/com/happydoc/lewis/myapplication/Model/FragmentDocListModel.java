package com.happydoc.lewis.myapplication.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.happydoc.lewis.myapplication.event.MyCallBack;
import com.happydoc.lewis.myapplication.fragmentinfo.CarouselItem;
import com.happydoc.lewis.myapplication.fragmentinfo.FragmentInfo;
import com.happydoc.lewis.myapplication.search.SearchCriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.security.auth.callback.Callback;

/**
 * Created by Lewis on 2016/7/18.
 */
public class FragmentDocListModel {
    public void getScrollImageData(MyCallBack<List<CarouselItem>> callBack){
        List<CarouselItem> list=new ArrayList<>();
        list.add(new CarouselItem("苍茫的天涯的是我的爱","http://ac-ph14qk1o.clouddn.com/98f293029d700a81.jpg"));
        list.add(new CarouselItem("空山新雨后冻住不许走","http://ac-ph14qk1o.clouddn.com/6036d2d2b2e138db.jpg"));
        list.add(new CarouselItem("飞流直下三千尺不如自挂东南枝","http://ac-ph14qk1o.clouddn.com/8525c1ae1330dc35.jpg"));
        //list.add(new CarouselItem("指引你的瑜伽资深大师的指引你的瑜伽资","http://www.jianyumei.com.cn/ueditor/net/upload/2015-01-15/e9af161b-133d-4017-989a-624e6771e158.jpg"));
        callBack.done(list);
    }
    public void getDocListData(SearchCriteria criteria, final MyCallBack<List<DoctorInfo>> callBack){
        AVQuery<AVObject> query=new AVQuery<>("Doctor");
        query.whereEqualTo("isChildMajor",criteria.getIsChileMajor());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e==null){
                    List<DoctorInfo> docList=new ArrayList<DoctorInfo>();
                    for(AVObject docItem:list)
                    {
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
                        docList.add(new DoctorInfo(avatarUrl,name,title,hosp,intro,feeOnceS,feeWeekS,docItem));
                    }
                    callBack.done(docList);

                }else
                {
                    Log.d("error",e.getMessage());
                }
            }
        });
    }
}
