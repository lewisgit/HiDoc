package com.happydoc.lewis.myapplication.Presenter;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.happydoc.lewis.myapplication.Model.SearchModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.SearchView;
import com.happydoc.lewis.myapplication.account.DoctorInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LewisMS on 7/28/2016.
 */
public class SearchPresenter {
    SearchView view;
    SearchModel model;
    public SearchPresenter(SearchView view,SearchModel model){
        this.view=view;
        this.model=model;
        initView();
    }
    public void initView(){
        view.setView();
        view.setBackListener();
        view.setListViewItemListener();
        view.setListAdapter();
        view.getEditText().setOnEditorActionListener(new myOnEditorActionListener());
        view.getSearchBtn().setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {searchDoc(view.getEditText().getText().toString());}});
    }
    public void searchDoc(String input){
        if(input.length()==0) return;
        AVQuery<AVObject> query=new AVQuery<>("Doctor");
        query.whereContains("Name", input);
        query.findInBackground(new FindCallback<AVObject>(){
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e==null){
                    if(list.size()==0)
                    {
                        view.showMsg(R.string.no_search_reasult);
                    }else{
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
                        view.setResultDocList(docList);
                    }}
                else{
                    view.showMsg(e.getMessage());
                }
            }
        });
    }



    public class myOnEditorActionListener implements TextView.OnEditorActionListener{

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                searchDoc(view.getEditText().getText().toString());
                return true;
            }
            return false;
        }
    }
}
