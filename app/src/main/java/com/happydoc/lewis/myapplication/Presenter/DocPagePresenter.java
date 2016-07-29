package com.happydoc.lewis.myapplication.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avoscloud.leanchatlib.activity.AVChatActivity;
import com.avoscloud.leanchatlib.utils.Constants;
import com.happydoc.lewis.myapplication.Bean.ConsultInfo;
import com.happydoc.lewis.myapplication.Bean.GlobalInfos;
import com.happydoc.lewis.myapplication.Bean.PayInfo;
import com.happydoc.lewis.myapplication.Model.DocPageModel;
import com.happydoc.lewis.myapplication.MyChatActivity;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.FragmentDocPageView;
import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.happydoc.lewis.myapplication.event.FollowEvent;
import com.happydoc.lewis.myapplication.event.MyCallBack;
import com.happydoc.lewis.myapplication.event.StartConsultEvent;
import com.happydoc.lewis.myapplication.utils.PayResult;
import com.happydoc.lewis.myapplication.utils.payUtils;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import de.greenrobot.event.EventBus;

/**
 * Created by LewisMS on 7/25/2016.
 */
public class DocPagePresenter implements GenPresenter {
    FragmentDocPageView view;DocPageModel model;
    private DoctorInfo doctorInfo;
    boolean followReady,isFollow;
    AVObject docUserObj;
    ConsultInfo curConInfo;
    //String convId;
    public DocPagePresenter(FragmentDocPageView view,DocPageModel model){
        this.view=view;
        this.model=model;
        EventBus.getDefault().register(this);
        GlobalInfos.addListRegObj(this);
        view.setView();
        initView();
        //docPageReady=false;
    }
    public void initView(){

        curConInfo=new ConsultInfo();
        curConInfo.setConsultState(ConsultInfo.INVALID);
        view.setConBtn(ConsultInfo.INVALID);
        followReady=false;
        view.setUnfollow();
        view.initCommentList();
        view.setCommentList(new ArrayList<AVObject>());
        loadDocInfo();
        if(doctorInfo!=null){
            if(doctorInfo.getDocObject()!=null){
                setDocUserObj();
                getFollowInfo();
                getConsultInfo();
                String id=doctorInfo.getDocObject().getString("DoctorID");
                if(id!=null)
                    getCommentList(id);
            }
        }
        view.setOnClickListener();
    }
    public void refreshView(){initView();}
    public void setDocUserObj(){
        docUserObj=doctorInfo.getDocObject().getAVObject("userPointer");
    }
    public void loadDocInfo(){
        doctorInfo= GlobalInfos.getCurDocInfo();
        if(doctorInfo!=null && view!=null)
        {view.showDocInfo(doctorInfo);}
    }
    public void getFollowInfo(){
        if(doctorInfo!=null)
            if(doctorInfo.getDocObject()!=null){
                model.isFollow(doctorInfo.getDocObject().getObjectId(), new MyCallBack<Boolean>() {
                    @Override
                    public void done(Boolean data) {
                        if(data==null){followReady=false;}else{
                            isFollow=data;
                            if(data){
                                view.setFollow();
                            }else{
                                view.setUnfollow();
                            }
                            followReady=true;
                        }
                    }
                });
            }else{
                followReady=false;
            }

    }
    public void getConsultInfo(){
        if(docUserObj!=null)
         model.getConsultState(docUserObj.getObjectId(), new MyCallBack<ConsultInfo>() {
             @Override
             public void done(ConsultInfo data) {
                 curConInfo=data;
                 view.setConBtn(data.getConsultState());
             }
         });
    }


    public void getCommentList(String id){
        AVQuery<AVObject> query=new AVQuery<>("Comment");
        query.whereEqualTo("DoctorID", id);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    view.setCommentList(list);
                }
            }
        });
    }



    public void onEvent(FollowEvent event){
        if(followReady && doctorInfo.getDocObject()!=null){
            if(isFollow){
                model.unfollowDoc(doctorInfo.getDocObject(), new MyCallBack<Exception>() {
                    @Override
                    public void done(Exception data) {
                        if(data==null){
                            view.showMsg(R.string.unfollow_success);
                            isFollow=false;
                            view.setUnfollow();
                        }else{
                            view.showMsg(R.string.unfollow_fail);
                        }
                    }
                });
            }else{
                model.followDoc(doctorInfo.getDocObject(), new MyCallBack<Exception>() {
                    @Override
                    public void done(Exception data) {
                        if(data==null){
                            view.showMsg(R.string.follow_success);
                            isFollow=true;
                            view.setFollow();
                        }else{
                            view.showMsg(R.string.follow_fail);
                        }
                    }
                });
            }
        }else{
            view.showMsg(R.string.page_not_ready);
        }
    }
    public void onEvent(StartConsultEvent event){
        if(curConInfo.getConsultState()==ConsultInfo.INVALID){
            view.showMsg("Connect Error!");
        }else{
            boolean payNeeded=true;
            if(event.getServiceTyp()==StartConsultEvent.ONCE_CONSULT && curConInfo.getConsultState()!=ConsultInfo.BOTH_UNAVAL){
                payNeeded=false;
            }else if(event.getServiceTyp()==StartConsultEvent.WEEK_CONSULT && curConInfo.getConsultState()==ConsultInfo.WEEK_AVAL){
                payNeeded=false;
            }
            if(!payNeeded){
                startConv();
                return;}
            PayInfo payInfo=getPayInfo(event,docUserObj);
            if(payInfo!=null){
                runPayInBackground(payInfo);
            }

        }
    }
    public PayInfo getPayInfo(StartConsultEvent event,AVObject doctor){
        String docID = doctorInfo.getDocObject().getString("DoctorID");
        String docName = doctorInfo.getDocObject().getString("Name");
        String payPrice ;
        String productTitle ;
        if(event.getServiceTyp()==StartConsultEvent.ONCE_CONSULT){
            if(doctorInfo.getDocObject().getNumber("feeOnce")==null ){
                view.showMsg(R.string.fee_not_set);
                return null;
            }else{
                payPrice = doctorInfo.getDocObject().getNumber("feeOnce").toString();
                productTitle = view.fragment.getString(R.string.consult_once);
            }
        }else{
            if( doctorInfo.getDocObject().getNumber("feeWeek")==null ){
                view.showMsg(R.string.fee_not_set);
                return null;
            }else{
                payPrice = doctorInfo.getDocObject().getNumber("feeWeek").toString();
                productTitle = view.fragment.getString(R.string.consult_week);
            }
        }
        String productDsp = AVUser.getCurrentUser().getObjectId()+(event.getServiceTyp()==StartConsultEvent.WEEK_CONSULT?1:0);
        String details = payUtils.getRequestString(doctor, productTitle, productDsp, payPrice);
        PayInfo payInfo=new PayInfo();
        payInfo.setDetails(details);
        payInfo.setPrice(payPrice);
        payInfo.setServiceType(event.getServiceTyp()==StartConsultEvent.ONCE_CONSULT?"1":"2");
        return payInfo;
    }
    public void runPayInBackground(final PayInfo payInfo){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask1 对象
                PayTask alipay = new PayTask(view.fragment.getActivity());
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo.getDetails(), true);

                Bundle data = new Bundle();
                data.putString("price", payInfo.getPrice());
                data.putString("type", payInfo.getServiceType());
                Message msg = new Message();
                msg.setData(data);
                msg.what = payUtils.SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        new Thread(payRunnable).start();
    }
    Handler mHandler=new Handler() {
        public void handleMessage(Message msg){
            PayResult payResult = new PayResult((String) msg.obj);
            /**
             * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
             * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
             * docType=1) 建议商户依赖异步通知
             */
            Bundle data = msg.getData();
            String price = data.getString("price");
            String type = data.getString("type");
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            //Toast.makeText(getActivity(), resultStatus, Toast.LENGTH_SHORT).show();
            if (TextUtils.equals(resultStatus, "9000")) {
                view.showMsg(R.string.pay_success);
                startConv();
                /*Toast.makeText(getActivity(), getString(R.string.pay_success), Toast.LENGTH_SHORT).show();
                //putBill(price,type);
                Intent intent = new Intent(getActivity(), MyChatActivity.class);
                //Toast.makeText(this, usrId.getText(), Toast.LENGTH_SHORT).show();
                Constants.currentDocID = doctor.getObjectId();
                Constants.serviceType=type;
                if(isExistConv){
                    intent.putExtra(Constants.CONVERSATION_ID, convId);
                }else{
                    intent.putExtra(Constants.MEMBER_ID, doctor.getObjectId());
                }
                getActivity().startActivity(intent);*/
            } else {
                // 判断resultStatus 为非"9000"则代表可能支付失败
                // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                if (TextUtils.equals(resultStatus, "8000")) {
                    view.showMsg(R.string.need_confirmation);
                    //Toast.makeText(getActivity(), getString(R.string.need_confirmation), Toast.LENGTH_SHORT).show();

                } else {
                    view.showMsg(R.string.pay_fail);
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                   // Toast.makeText(getActivity(), getString(R.string.pay_fail) + '(' + resultStatus + ')', Toast.LENGTH_SHORT).show();

                }
            }

            //Toast.makeText(getActivity(), "pay test success!!", Toast.LENGTH_SHORT);

        }
    };

    public void startConv(){
        Intent intent = new Intent(view.fragment.getActivity(), MyChatActivity.class);
        //Toast.makeText(this, usrId.getText(), Toast.LENGTH_SHORT).show();
        //Constants.currentDocID = doctor.getObjectId();
        //Constants.serviceType=type;
        if(curConInfo.getConvId()!=null){
            intent.putExtra(Constants.CONVERSATION_ID, curConInfo.getConvId());
        }else{
            intent.putExtra(Constants.MEMBER_ID, docUserObj.getObjectId());
        }
        view.fragment.getActivity().startActivity(intent);
    }
}
