package com.happydoc.lewis.myapplication.Presenter;

import android.support.v4.widget.SwipeRefreshLayout;

import com.happydoc.lewis.myapplication.Model.FollowModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.FollowView;
import com.happydoc.lewis.myapplication.View.FragmentView;
import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.happydoc.lewis.myapplication.event.MyCallBack;

import java.util.List;

/**
 * Created by Lewis on 2016/7/26.
 */
public class FollowPresenter {
    FollowView view;
    FollowModel model;
    public  FollowPresenter(FollowView view,FollowModel model){
        this.view=view;
        this.model=model;
        initView();
    }
    public void initView(){
        view.setView();
        view.setListViewAdapter();
        loadData();
        view.setListener();
        if(view.getRefreshLayout()!=null)view.getRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }
    public void loadData(){
        model.getMyFollow(new MyCallBack<List<DoctorInfo>>() {
            @Override
            public void done(List<DoctorInfo> data) {
                if(view.getRefreshLayout()!=null);
                view.getRefreshLayout().setRefreshing(false);
                if(data!=null){
                    view.setListData(data);
                }else{
                    view.showMsg(R.string.load_fail);
                }
            }
        });
    }
}
