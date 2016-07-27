package com.happydoc.lewis.myapplication.View;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.avos.avoscloud.AVObject;
import com.happydoc.lewis.myapplication.Bean.GlobalInfos;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.happydoc.lewis.myapplication.adapter.AdapterDocList;
import com.happydoc.lewis.myapplication.event.MainActivityEventBus;
import com.happydoc.lewis.myapplication.event.SearchDocEvent;
import com.happydoc.lewis.myapplication.event.ShowFragmentEvent;
import com.happydoc.lewis.myapplication.fragmentinfo.CarouselItem;
import com.happydoc.lewis.myapplication.scrollimage.common.ScrollImageEntity;
import com.happydoc.lewis.myapplication.scrollimage.image.ImageScroll;
import com.happydoc.lewis.myapplication.scrollimage.image.ScrollImageController;
import com.happydoc.lewis.myapplication.search.SearchCriteria;
import com.joanzapata.android.QuickAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lewis on 2016/7/18.
 */
public class FragmentDocListView extends FragmentView {

    ImageScroll myPager; // 图片容器
    LinearLayout ovalLayout; // 圆点容器
    ListView listView;
    Button doc1Btn;
    Button doc2Btn;
    ArrayAdapter<DoctorInfo> listViewAdapter;
    private ScrollImageController scrollImageView;
    private List<ScrollImageEntity> scrollImageList;

    public FragmentDocListView(Fragment fragment, View view){
        super(fragment,view);
    }
    public void initView(){
        listView=(ListView) view.findViewById(R.id.docList_View);
        View headerView=ListView.inflate(fragment.getActivity(),R.layout.ad_scroll_image,null);
        View headerBtn=ListView.inflate(fragment.getActivity(),R.layout.doclist_btn,null);
        doc1Btn=(Button)headerBtn.findViewById(R.id.doclist1);
        doc2Btn=(Button)headerBtn.findViewById(R.id.doclist2);
        listView.addHeaderView(headerView);
        listView.addHeaderView(headerBtn);
        //adapter.set
        listViewAdapter = new AdapterDocList(fragment.getActivity(),R.layout.docitem,new ArrayList<DoctorInfo>());
        listView.setAdapter(listViewAdapter);
        listView.setDividerHeight(0);
        initScrollView(headerView);//initialize the carousel view
    }
    //-----initialize the carousel view-------------
    private void initScrollView(View view) {
        myPager = (ImageScroll)view.findViewById(R.id.myvp);
        ovalLayout = (LinearLayout) view.findViewById(R.id.vb);
    }
    public void initScrollData(List<CarouselItem> list){
        scrollImageView = new ScrollImageController(fragment.getActivity());
        scrollImageList = new ArrayList<ScrollImageEntity>();
        for(CarouselItem item:list)
        {
            ScrollImageEntity scrollImageEntity = new ScrollImageEntity();
            scrollImageEntity.setTitle(item.subtitle);
            scrollImageEntity.setImageUrl(item.url);
            scrollImageList.add(scrollImageEntity);
            //scrollImageView.initViewPagerImage(list, myPager, ovalLayout);
        }
        scrollImageView.initViewPagerImage(scrollImageList, myPager, ovalLayout);
    }
    //------------------------------------------------

    //--------------Doctor List Set---------------
    public void setListViewData(List<DoctorInfo> list){
        listViewAdapter.clear();
        listViewAdapter.addAll(list);
        listViewAdapter.notifyDataSetChanged();
    }
    //-------------------------------------------

    public void setListVIewItemClickListener(){
        doc1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDocEvent event=new SearchDocEvent();
                SearchCriteria criteria=new SearchCriteria();
                criteria.setChildMajor(false);
                event.setCriteria(criteria);
                MainActivityEventBus.getEventBus().post(event);
            }
        });
        doc2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDocEvent event=new SearchDocEvent();
                SearchCriteria criteria=new SearchCriteria();
                criteria.setChildMajor(true);
                event.setCriteria(criteria);
                MainActivityEventBus.getEventBus().post(event);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoctorInfo doctorInfo=listViewAdapter.getItem(position-2);
                if(doctorInfo!=null){
                    GlobalInfos.setCurDocInfo(doctorInfo);
                    MainActivityEventBus.getEventBus().post(new ShowFragmentEvent(R.string.docpage_script));
                    GlobalInfos.addBackStack(GlobalInfos.getFragmentList().get(fragment.getString(R.string.doclist_script)));
                }
            }
        });
    }
    public void setOnDoc1Btn(){
        doc1Btn.setBackgroundResource(R.drawable.square_button);
        doc2Btn.setBackgroundResource(R.drawable.square_button_grey);
    }
    public void setOnDoc2Btn(){
        doc2Btn.setBackgroundResource(R.drawable.square_button);
        doc1Btn.setBackgroundResource(R.drawable.square_button_grey);
    }
 /*   private List<String> getData(){

        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        data.add("测试数据4");
        return data;
    }*/
}
