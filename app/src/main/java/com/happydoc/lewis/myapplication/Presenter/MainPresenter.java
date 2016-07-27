package com.happydoc.lewis.myapplication.Presenter;

import android.app.Fragment;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.avoscloud.leanchatlib.activity.AVChatActivity;
import com.avoscloud.leanchatlib.utils.Constants;
import com.happydoc.lewis.myapplication.Bean.GlobalInfos;
import com.happydoc.lewis.myapplication.CircleSendActivity;
import com.happydoc.lewis.myapplication.MainActivity;
import com.happydoc.lewis.myapplication.Model.MainModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.MainActivityView;
import com.happydoc.lewis.myapplication.event.ClickConversationEvent;
import com.happydoc.lewis.myapplication.event.GoBackEvent;
import com.happydoc.lewis.myapplication.event.SearchDocEvent;
import com.happydoc.lewis.myapplication.event.ShowFragmentEvent;
import com.happydoc.lewis.myapplication.event.StartSendCircleEvent;
import com.happydoc.lewis.myapplication.fragment.ChatListFragment;
import com.happydoc.lewis.myapplication.fragment.CircleFragment;
import com.happydoc.lewis.myapplication.fragment.DocListFragment;
import com.happydoc.lewis.myapplication.fragment.DocPageFragment;
import com.happydoc.lewis.myapplication.fragment.DocSearchFragment;
import com.happydoc.lewis.myapplication.fragment.FollowFragment;
import com.happydoc.lewis.myapplication.fragment.MeFragment;
import com.happydoc.lewis.myapplication.fragment.MeInfoFragment;
import com.happydoc.lewis.myapplication.fragment.SettingFragment;
import com.happydoc.lewis.myapplication.fragment.VideoFragment;
import com.happydoc.lewis.myapplication.fragmentinfo.FragmentInfo;
import com.happydoc.lewis.myapplication.search.SearchCriteria;

import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lewis on 2016/7/17.
 */
public class MainPresenter {
    public MainActivityView view;
    public MainModel model;
    public List<FragmentInfo> fragmentInfos=new ArrayList<>();//should add into model
    public MainPresenter(MainActivityView view,MainModel model){
        this.view=view;
        this.model=model;
        initialize();
    }
    public void initialize(){
        view.setView();
        view.setEventBus(new EventBus());
        view.getEventBus().register(this);
        view.setBtnOnClickListener();
        view.setFragmentRegionId(R.id.fragment_region);
        setFragmentInfoList();
        view.setFragmentList(fragmentInfos);
        setDefaultFragment();
    }
    public void setDefaultFragment(){
        int fragmentKey=R.string.doclist_script;
        view.showFragment(fragmentKey);
    }
    public void setFragmentInfoList(){
        FragmentInfo fragmentInfo1=new FragmentInfo();
        fragmentInfo1.fragment=new DocListFragment();
        fragmentInfo1.fragmentId=view.activity.getString(R.string.doclist_script);
       // fragmentInfo1.lightBtn=R.id.docList_btn;
        fragmentInfo1.setGoBack(false);
        fragmentInfo1.setShowTopBar(true);
        fragmentInfo1.fragmentTitle=view.getString(R.string.doclist_script);
        fragmentInfo1.pressRes=R.drawable.chat_press;
        fragmentInfo1.releaseRes=R.drawable.chat;
        fragmentInfo1.btnImg=(ImageView)view.getView(R.id.docList_btn);
        fragmentInfo1.btnText=(TextView) view.getView(R.id.doclist_btn_text);
        fragmentInfos.add(fragmentInfo1);

        FragmentInfo fragmentInfo2=new FragmentInfo();
        fragmentInfo2.fragment=new VideoFragment();
        fragmentInfo2.fragmentId=view.activity.getString(R.string.video_script);
        fragmentInfo2.setGoBack(false);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        fragmentInfo2.pressRes=R.drawable.video_press;
        fragmentInfo2.releaseRes=R.drawable.video;
        fragmentInfo2.setShowTopBar(true);
        fragmentInfo2.fragmentTitle=view.getString(R.string.video_script);
        fragmentInfo2.btnImg=(ImageView)view.getView(R.id.video_btn);
        fragmentInfo2.btnText=(TextView) view.getView(R.id.video_btn_text);
        fragmentInfos.add(fragmentInfo2);

        FragmentInfo fragmentInfo3=new FragmentInfo();
        fragmentInfo3.fragment=new CircleFragment();
        fragmentInfo3.fragmentId=view.activity.getString(R.string.circle_scriptt);
        fragmentInfo3.setGoBack(false);
        fragmentInfo3.setShowTopBar(true);
        fragmentInfo3.fragmentTitle=view.getString(R.string.circle_scriptt);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        fragmentInfo3.pressRes=R.drawable.circle_press;
        fragmentInfo3.releaseRes=R.drawable.circle;
        fragmentInfo3.btnImg=(ImageView)view.getView(R.id.circle_btn);
        fragmentInfo3.btnText=(TextView) view.getView(R.id.circle_btn_text);
        fragmentInfos.add(fragmentInfo3);

        FragmentInfo fragmentInfo4=new FragmentInfo();
        fragmentInfo4.fragment=new MeFragment();
        fragmentInfo4.setGoBack(false);
        fragmentInfo4.fragmentId=view.activity.getString(R.string.me_script);
        fragmentInfo4.fragmentTitle=view.getString(R.string.me_script);
        fragmentInfo4.setShowTopBar(true);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        fragmentInfo4.pressRes=R.drawable.me_press;
        fragmentInfo4.releaseRes=R.drawable.me;
        fragmentInfo4.btnImg=(ImageView)view.getView(R.id.me_btn);
        fragmentInfo4.btnText=(TextView) view.getView(R.id.me_btn_text);
        fragmentInfos.add(fragmentInfo4);

        FragmentInfo fragmentInfo5=new FragmentInfo();
        fragmentInfo5.fragment=new SettingFragment();
        fragmentInfo5.fragmentId=view.activity.getString(R.string.set_script);
        fragmentInfo5.fragmentTitle=view.getString(R.string.set_script);
        fragmentInfo5.setGoBack(true);
        fragmentInfo5.setShowTopBar(true);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        //fragmentInfo4.pressRes=R.drawable.me_press;
       // fragmentInfo4.releaseRes=R.drawable.me;
       // fragmentInfo4.btnImg=(ImageView)view.getView(R.id.me_btn);
        //fragmentInfo4.btnText=(TextView) view.getView(R.id.me_btn_text);
        fragmentInfos.add(fragmentInfo5);

        FragmentInfo fragmentInfo6=new FragmentInfo();
        fragmentInfo6.fragment=new MeInfoFragment();
        fragmentInfo6.fragmentId=view.activity.getString(R.string.info_script);
        fragmentInfo6.fragmentTitle=view.getString(R.string.info_script);
        fragmentInfo6.setGoBack(true);
        fragmentInfo6.setShowTopBar(true);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        //fragmentInfo4.pressRes=R.drawable.me_press;
        // fragmentInfo4.releaseRes=R.drawable.me;
        // fragmentInfo4.btnImg=(ImageView)view.getView(R.id.me_btn);
        //fragmentInfo4.btnText=(TextView) view.getView(R.id.me_btn_text);
        fragmentInfos.add(fragmentInfo6);

        FragmentInfo fragmentInfo7=new FragmentInfo();
        fragmentInfo7.fragment=new ChatListFragment();
        fragmentInfo7.fragmentId=view.activity.getString(R.string.chatlist_script);
        fragmentInfo7.fragmentTitle=view.getString(R.string.chatlist_script);
        fragmentInfo7.setGoBack(true);
        fragmentInfo7.setShowTopBar(true);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        //fragmentInfo4.pressRes=R.drawable.me_press;
        // fragmentInfo4.releaseRes=R.drawable.me;
        // fragmentInfo4.btnImg=(ImageView)view.getView(R.id.me_btn);
        //fragmentInfo4.btnText=(TextView) view.getView(R.id.me_btn_text);
        fragmentInfos.add(fragmentInfo7);

        FragmentInfo fragmentInfo8=new FragmentInfo();
        fragmentInfo8.fragment=new DocPageFragment();
        fragmentInfo8.fragmentId=view.activity.getString(R.string.docpage_script);
        fragmentInfo8.fragmentTitle=view.getString(R.string.docpage_script);
        fragmentInfo8.setIsShowReload(true);
        fragmentInfo8.setGoBack(true);
        fragmentInfo8.setShowTopBar(true);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        //fragmentInfo4.pressRes=R.drawable.me_press;
        // fragmentInfo4.releaseRes=R.drawable.me;
        // fragmentInfo4.btnImg=(ImageView)view.getView(R.id.me_btn);
        //fragmentInfo4.btnText=(TextView) view.getView(R.id.me_btn_text);
        fragmentInfos.add(fragmentInfo8);

        FragmentInfo fragmentInfo9=new FragmentInfo();
        fragmentInfo9.fragment=new FollowFragment();
        fragmentInfo9.fragmentId=view.activity.getString(R.string.myfollow_script);
        fragmentInfo9.fragmentTitle=view.getString(R.string.myfollow_script);
        fragmentInfo9.setGoBack(true);
        fragmentInfo9.setShowTopBar(true);
        fragmentInfo9.setIsShowReload(false);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        //fragmentInfo4.pressRes=R.drawable.me_press;
        // fragmentInfo4.releaseRes=R.drawable.me;
        // fragmentInfo4.btnImg=(ImageView)view.getView(R.id.me_btn);
        //fragmentInfo4.btnText=(TextView) view.getView(R.id.me_btn_text);
        fragmentInfos.add(fragmentInfo9);

        FragmentInfo fragmentInfo10=new FragmentInfo();
        fragmentInfo10.fragment=new DocSearchFragment();
        fragmentInfo10.fragmentId=view.activity.getString(R.string.search_script);
        fragmentInfo10.fragmentTitle=view.getString(R.string.search_script);
        fragmentInfo10.setGoBack(true);
        fragmentInfo10.setShowTopBar(false);
        fragmentInfo10.setIsShowReload(false);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        //fragmentInfo4.pressRes=R.drawable.me_press;
        // fragmentInfo4.releaseRes=R.drawable.me;
        // fragmentInfo4.btnImg=(ImageView)view.getView(R.id.me_btn);
        //fragmentInfo4.btnText=(TextView) view.getView(R.id.me_btn_text);
        fragmentInfos.add(fragmentInfo10);
    }

   // @Subscribe
    public void onEvent(ShowFragmentEvent event){
        view.showFragment(event.fragmentId);
    }

   // @Subscribe
    public void onEvent(StartSendCircleEvent event){
        Intent intent=new Intent(view.activity, CircleSendActivity.class);
        view.activity.startActivity(intent);
    }
  //  @Subscribe
    public void onEvent(ClickConversationEvent event){
        Intent intent = new Intent(view.activity, AVChatActivity.class);
        intent.putExtra(Constants.CONVERSATION_ID, event.getConvId());
        view.activity.startActivity(intent);
    }
    public void onEvent(GoBackEvent event){
        List<FragmentInfo> list= GlobalInfos.getBackStack();
        if (list.size() > 0) {
            if (view.currentFragmentInfo.getGoBack()) {
                view.showFragment(list.get(list.size()-1).fragmentId);
                GlobalInfos.popBackStack();
            }
        }
    }
}
