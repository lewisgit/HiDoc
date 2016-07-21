package com.happydoc.lewis.myapplication.Presenter;

import android.app.Fragment;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.happydoc.lewis.myapplication.CircleSendActivity;
import com.happydoc.lewis.myapplication.MainActivity;
import com.happydoc.lewis.myapplication.Model.MainModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.MainActivityView;
import com.happydoc.lewis.myapplication.event.ShowFragmentEvent;
import com.happydoc.lewis.myapplication.event.StartSendCircleEvent;
import com.happydoc.lewis.myapplication.fragment.CircleFragment;
import com.happydoc.lewis.myapplication.fragment.DocListFragment;
import com.happydoc.lewis.myapplication.fragment.DocPageFragment;
import com.happydoc.lewis.myapplication.fragmentinfo.FragmentInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lewis on 2016/7/17.
 */
public class MainPresenter {
    public MainActivityView view;
    public MainModel model;
    public List<FragmentInfo> fragmentInfos=new ArrayList<>();
    public MainPresenter(MainActivityView view,MainModel model){
        this.view=view;
        this.model=model;
        initialize();
    }
    public void initialize(){
        view.setEventBus(new EventBus());
        view.getEventBus().register(this);
        view.setBtnOnClickListener();
        view.setFragmentRegionId(R.id.fragment_region);
        setFragmentInfoList();
        view.setFragmentList(fragmentInfos);
        setDefaultFragment();
    }
    public void setDefaultFragment(){
        int fragmentKey=R.string.doclist_fragment;
        view.showFragment(fragmentKey);
    }
    public void setFragmentInfoList(){
        FragmentInfo fragmentInfo1=new FragmentInfo();
        fragmentInfo1.fragment=new DocListFragment();
        fragmentInfo1.fragmentId=view.activity.getString(R.string.doclist_fragment);
       // fragmentInfo1.lightBtn=R.id.docList_btn;
        fragmentInfo1.pressRes=R.drawable.chat_press;
        fragmentInfo1.releaseRes=R.drawable.chat;
        fragmentInfo1.btnImg=(ImageView)view.getView(R.id.docList_btn);
        fragmentInfo1.btnText=(TextView) view.getView(R.id.doclist_btn_text);
        fragmentInfos.add(fragmentInfo1);
        FragmentInfo fragmentInfo2=new FragmentInfo();
        fragmentInfo2.fragment=new DocPageFragment();
        fragmentInfo2.fragmentId=view.activity.getString(R.string.docpage_fragment);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        fragmentInfo2.pressRes=R.drawable.video_press;
        fragmentInfo2.releaseRes=R.drawable.video;
        fragmentInfo2.btnImg=(ImageView)view.getView(R.id.video_btn);
        fragmentInfo2.btnText=(TextView) view.getView(R.id.video_btn_text);
        fragmentInfos.add(fragmentInfo2);

        FragmentInfo fragmentInfo3=new FragmentInfo();
        fragmentInfo3.fragment=new CircleFragment();
        fragmentInfo3.fragmentId=view.activity.getString(R.string.circle_fragment);
        // fragmentInfo1.lightBtn=R.id.docList_btn;
        fragmentInfo3.pressRes=R.drawable.circle_press;
        fragmentInfo3.releaseRes=R.drawable.circle;
        fragmentInfo3.btnImg=(ImageView)view.getView(R.id.circle_btn);
        fragmentInfo3.btnText=(TextView) view.getView(R.id.circle_btn_text);
        fragmentInfos.add(fragmentInfo3);
    }

    @Subscribe
    public void onEvent(ShowFragmentEvent event){
        view.showFragment(event.fragmentId);
    }

    @Subscribe
    public void onEvent(StartSendCircleEvent event){
        Intent intent=new Intent(view.activity, CircleSendActivity.class);
        view.activity.startActivity(intent);
    }
}
