package com.happydoc.lewis.myapplication.View;

import android.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.happydoc.lewis.myapplication.Bean.GlobalInfos;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.happydoc.lewis.myapplication.adapter.AdapterDocList;
import com.happydoc.lewis.myapplication.event.GoBackEvent;
import com.happydoc.lewis.myapplication.event.MainActivityEventBus;
import com.happydoc.lewis.myapplication.event.ShowFragmentEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LewisMS on 7/28/2016.
 */
public class SearchView extends FragmentView {
    TextView searchBtn;
    ImageView backBtn;
    ListView resultList;
    EditText editText;
    AdapterDocList listViewAdapter;
    public SearchView(Fragment fragment, View view){
        super(fragment,view);
    }
    public void setView(){
        searchBtn=(TextView)getView(R.id.search_btn);
        backBtn=(ImageView)getView(R.id.back_search);
        editText=(EditText)getView(R.id.search_input);
        resultList=(ListView)getView(R.id.search_result);
    }
    public void setListAdapter(){
        listViewAdapter = new AdapterDocList(fragment.getActivity(),R.layout.docitem,new ArrayList<DoctorInfo>());
        resultList.setAdapter(listViewAdapter);
    }

    public void setListViewItemListener() {
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoctorInfo doctorInfo=listViewAdapter.getItem(position);
                if(doctorInfo!=null){
                    GlobalInfos.setCurDocInfo(doctorInfo);
                    MainActivityEventBus.getEventBus().post(new ShowFragmentEvent(R.string.docpage_script));
                    GlobalInfos.addBackStack(GlobalInfos.getFragmentList().get(fragment.getString(R.string.search_script)));
                }
            }
        });
    }

    public void setBackListener(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityEventBus.getEventBus().post(new GoBackEvent());
            }
        });
    }
    public void setResultDocList(List<DoctorInfo> list){
        listViewAdapter.clear();
        listViewAdapter.addAll(list);
    }

    public EditText getEditText() {
        return editText;
    }

    public TextView getSearchBtn() {
        return searchBtn;
    }
}
