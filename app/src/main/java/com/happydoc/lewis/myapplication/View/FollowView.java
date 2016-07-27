package com.happydoc.lewis.myapplication.View;

import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.happydoc.lewis.myapplication.Bean.GlobalInfos;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.happydoc.lewis.myapplication.adapter.AdapterDocList;
import com.happydoc.lewis.myapplication.event.MainActivityEventBus;
import com.happydoc.lewis.myapplication.event.ShowFragmentEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lewis on 2016/7/26.
 */
public class FollowView extends FragmentView{
    ArrayAdapter<DoctorInfo> listViewAdapter;
    SwipeRefreshLayout refreshLayout;
    ListView followList;
    public FollowView(Fragment fragment, View view){
        super(fragment,view);
    }
    public void setView(){
        followList=(ListView)getView(R.id.follow_list);
        refreshLayout=(SwipeRefreshLayout)getView(R.id.follow_refresh);
    }
    public void setListViewAdapter(){
        listViewAdapter = new AdapterDocList(fragment.getActivity(),R.layout.docitem,new ArrayList<DoctorInfo>());
        if(followList!=null){
            followList.setAdapter(listViewAdapter);
        }
    }
    public void setListData(List<DoctorInfo> listData){
        if(listViewAdapter!=null)
        {
            listViewAdapter.clear();
            listViewAdapter.addAll(listData);
            listViewAdapter.notifyDataSetChanged();
        }
    }
    public void setListener(){
        followList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoctorInfo doctorInfo=listViewAdapter.getItem(position);
                GlobalInfos.setCurDocInfo(doctorInfo);
                MainActivityEventBus.getEventBus().post(new ShowFragmentEvent(R.string.docpage_script));
                GlobalInfos.addBackStack(GlobalInfos.getFragmentList().get(fragment.getString(R.string.myfollow_script)));
            }
        });
    }
    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }
}
