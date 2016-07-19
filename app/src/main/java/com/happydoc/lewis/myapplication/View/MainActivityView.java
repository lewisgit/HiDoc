package com.happydoc.lewis.myapplication.View;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.happydoc.lewis.myapplication.App;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.event.ShowFragmentEvent;
import com.happydoc.lewis.myapplication.fragment.DocListFragment;
import com.happydoc.lewis.myapplication.fragmentinfo.FragmentInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lewis on 2016/7/15.
 */
public class MainActivityView extends MotherView implements ActivityView{
    public Fragment currentFragment;
    public FragmentInfo currentFragmentInfo;
    public int fragmentRegionId;
    public EventBus eventBus;
    public HashMap<String,FragmentInfo> fragmentList=new HashMap<>();
    public MainActivityView(AppCompatActivity activity){
        super(activity);
        //setFragmentList();
    }
    public void setFragmentList(List<FragmentInfo> fragmentInfos){
        for(FragmentInfo fragmentInfo:fragmentInfos){
            fragmentList.put(fragmentInfo.fragmentId, fragmentInfo);
        }
    }
    @Override
    public void showFragment(int id){
        FragmentInfo fragmentInfo=fragmentList.get(activity.getString(id));
        Fragment fragment=fragmentInfo.fragment;
        FragmentTransaction fragmentTransaction=activity.getFragmentManager().beginTransaction();
        if(currentFragment!=fragment && fragment!=null){
            if(currentFragment!=null){
            if(fragment.isAdded()) {
                fragmentTransaction.hide(currentFragment).show(fragment).commit();
            }else{
                fragmentTransaction.hide(currentFragment).add(fragmentRegionId,fragment).commit();
            }
                if(fragmentInfo.btnImg!=null)
                    setBtn(fragmentInfo.btnImg,fragmentInfo.pressRes,fragmentInfo.btnText,R.color.doc_blue);
                if(currentFragmentInfo.btnImg!=null)
                    setBtn(currentFragmentInfo.btnImg,currentFragmentInfo.releaseRes,currentFragmentInfo.btnText,R.color.doc_gray);
            }else{
                fragmentTransaction.add(fragmentRegionId,fragment).commit();
                if(fragmentInfo.btnImg!=null)
                    setBtn(fragmentInfo.btnImg,fragmentInfo.pressRes,fragmentInfo.btnText,R.color.doc_blue);
            }
            currentFragment=fragment;
            currentFragmentInfo=fragmentInfo;
        }
    }
    public void setBtn(ImageView view, int resId, TextView textView,int color)
    {
        view.setImageResource(resId);
        textView.setTextColor(ContextCompat.getColor(activity,color));
    }
    public void setFragmentRegionId(int id){
        fragmentRegionId=id;
    }
    public void setEventBus(EventBus eventBus){
        this.eventBus=eventBus;
    }
    public EventBus getEventBus(){
        if(eventBus==null){
            eventBus=new EventBus();
            return eventBus;
        }else{return eventBus;}
    }

    public void setBtnOnClickListener(){

        TextView doclistBtnText=(TextView) activity.findViewById(R.id.doclist_btn_text);
        ImageView doclistBtn=(ImageView) activity.findViewById(R.id.docList_btn);
        View.OnClickListener listener1=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.post(new ShowFragmentEvent(R.string.doclist_fragment));
                showMsg("testBtn!");}};
        doclistBtn.setOnClickListener(listener1);
        doclistBtnText.setOnClickListener(listener1);

        TextView videoBtnText=(TextView) activity.findViewById(R.id.video_btn_text);
        ImageView videoBtn=(ImageView) activity.findViewById(R.id.video_btn);
        View.OnClickListener listener2=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.post(new ShowFragmentEvent(R.string.docpage_fragment));
                showMsg("testBtn!");}};
        videoBtn.setOnClickListener(listener2);
        videoBtnText.setOnClickListener(listener2);
    }
}
