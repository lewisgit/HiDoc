package com.happydoc.lewis.myapplication.Model;

import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.SaveCallback;
import com.happydoc.lewis.myapplication.Bean.ConsultInfo;
import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.happydoc.lewis.myapplication.event.MyCallBack;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LewisMS on 7/25/2016.
 */
public class DocPageModel {
    public void isFollow(final String docId, final MyCallBack<Boolean> callBack){
        AVUser currentUser=AVUser.getCurrentUser();
        if(currentUser!=null){
        currentUser.getRelation("Followee").getQuery().findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> results, AVException e) {
                if (e != null) {
                    Log.d("query error found:", e.getMessage());
                    callBack.done(null);
                } else {
                    // 当前用户喜欢的所有 Post 都保存在 results 里面了.
                    for (AVObject objectDoc : results) {
                        if (objectDoc.getObjectId().equals(docId)) {
                            //followBtn.setBackgroundResource(R.drawable.corners_gray);
                            //isFollow=true;
                            //followBtn.setText(getString(R.string.unfollow));
                            callBack.done(true);
                            break;
                        }
                    }
                    callBack.done(false);
                }
            }
        });
        }else{
            callBack.done(null);
        }
    }
    public void getConsultState(String docId, final MyCallBack<ConsultInfo> callBack){
        final ConsultInfo consultInfo=new ConsultInfo();
        consultInfo.setConsultState(ConsultInfo.INVALID);
        try{
            Map<String, Object> dicParameters = new HashMap<>();
       /* dicParameters.put("postid", "");
        dicParameters.put("q", 3);*/
            if(AVUser.getCurrentUser()!=null && docId!=null){
            dicParameters.put("mem", Arrays.asList(docId, AVUser.getCurrentUser().getObjectId()));
            // 调用云函数
            AVCloud.callFunctionInBackground("mem_q", dicParameters, new FunctionCallback<List<Object>>() {
                        @Override
                        public void done(List<Object> o, AVException e) {

                            if (e == null) {
                                int convStatus = Integer.parseInt(o.get(0).toString());
                                if (convStatus == 1) {
                                    consultInfo.setConvId(o.get(2).toString());
                                    //isExistConv = true;
                                    //convId = o.get(2).toString();
                                    BigDecimal remTime_d = (BigDecimal) o.get(1);
                                    long expireTime = remTime_d.longValue();
                                    if (expireTime < 0) {
                                        consultInfo.setConsultState(ConsultInfo.BOTH_UNAVAL);
                                       /* ispay1 = 1;
                                        ispay2 = 1;
                                        payBtn1.setBackgroundResource(R.drawable.corners_orangered);
                                        payBtn2.setText(getString(R.string.pay_now));
                                        payBtn2.setBackgroundResource(R.drawable.corners_orangered);
                                        payBtn2.setText(getString(R.string.pay_now));*/
                                    } else {
                                        if (expireTime < 120) {
                                           consultInfo.setConsultState(ConsultInfo.ONCE_AVAL);
                                        } else {
                                            consultInfo.setConsultState(ConsultInfo.WEEK_AVAL);
                                           /* ispay1 = 2;
                                            ispay2 = 2;
                                            payBtn1.setBackgroundResource(R.drawable.corners_blue);
                                            payBtn1.setText(getString(R.string.chat_now));
                                            payBtn2.setBackgroundResource(R.drawable.corners_blue);
                                            payBtn2.setText(getString(R.string.chat_now));*/
                                        }
                                    }
                                } else {
                                    consultInfo.setConsultState(ConsultInfo.BOTH_UNAVAL);
                                   /* isExistConv = false;
                                    convId = "";
                                    ispay1 = 1;
                                    ispay2 = 1;*/
                                }
                            /*Object attr = o.get(0);
                            Object nowTime = o.get(1);
                            long nowTimelong = Long.parseLong(nowTime == null ? "" : nowTime.toString());
                            HashMap<String, Object> attrs = (HashMap<String, Object>) attr;
                            if(!attrs.isEmpty()){
                            long expireTime = Long.parseLong(attrs.get("expire").toString());
                            if (expireTime - nowTimelong > 0) {
                                if (expireTime - nowTimelong > 30) {
                                    ispay1 = 2;
                                    ispay2 = 2;
                                    payBtn1.setBackgroundResource(R.drawable.corners_blue);
                                    payBtn1.setText(getString(R.string.chat_now));
                                    payBtn2.setBackgroundResource(R.drawable.corners_blue);
                                    payBtn2.setText(getString(R.string.chat_now));
                                } else {
                                    ispay1 = 2;
                                    ispay2 = 1;
                                    payBtn1.setBackgroundResource(R.drawable.corners_blue);
                                    payBtn1.setText(getString(R.string.chat_now));
                                    payBtn2.setBackgroundResource(R.drawable.corners_red);
                                    payBtn2.setText(getString(R.string.pay_now));
                                }
                            } else {
                                ispay1 = 1;
                                ispay2 = 1;
                                payBtn1.setBackgroundResource(R.drawable.corners_red);
                                payBtn2.setText(getString(R.string.pay_now));
                                payBtn2.setBackgroundResource(R.drawable.corners_red);
                                payBtn2.setText(getString(R.string.pay_now));
                            }
                        }*/
                            } else {
                                consultInfo.setConsultState(ConsultInfo.INVALID);
                            }
                            callBack.done(consultInfo);
                        }
                    }
            );}else{callBack.done(consultInfo);}
        }
        catch (IllegalStateException e){e.printStackTrace();

            callBack.done(consultInfo);
        }}

    public void unfollowDoc(final AVObject docObj,final MyCallBack<Exception> callBack){
        AVUser currentUser=AVUser.getCurrentUser();
        currentUser.getRelation("Followee").remove(docObj);
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    docObj.increment("countFollower", -1);
                    docObj.increment("followerForWeek", -1);
                    docObj.saveInBackground();
                    callBack.done(e);
                    //followBtn.setBackgroundResource(R.drawable.corners);
                    //isFollow=false;
                    //followBtn.setText(getString(R.string.follow));
                    //Toast.makeText(getActivity(), getString(R.string.unfollow_success), Toast.LENGTH_LONG).show();
                } else {
                    callBack.done(e);
                    // Toast.makeText(getActivity(),getString(R.string.unfollow_fail),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void followDoc(final AVObject docObj,final MyCallBack<Exception> callBack){
        AVUser currentUser=AVUser.getCurrentUser();
        //if(currentUser.getRelation("Followee").)
        currentUser.getRelation("Followee").add(docObj);
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e==null)
                {
                    docObj.increment("countFollower");
                    docObj.increment("followerForWeek");
                    docObj.saveInBackground();
                    callBack.done(e);
                    //followBtn.setBackgroundResource(R.drawable.corners_gray);
                    //isFollow=true;
                    //followBtn.setText(getString(R.string.unfollow));
                   // Toast.makeText(getActivity(),getString(R.string.follow_success),Toast.LENGTH_LONG).show();
                }
                else
                {callBack.done(e);
                    //Toast.makeText(getActivity(),getString(R.string.follow_fail),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}