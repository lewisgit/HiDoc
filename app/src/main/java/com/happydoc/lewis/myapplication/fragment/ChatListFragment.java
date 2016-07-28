package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happydoc.lewis.myapplication.Model.ChatListModel;
import com.happydoc.lewis.myapplication.Presenter.ChatListPresenter;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.ChatListView;

/**
 * Created by Lewis on 2016/7/24.
 */
public class ChatListFragment extends GeneralFragment {
    private ChatListPresenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_chatlist, container, false);
        ChatListView view=new ChatListView(this,myView);
        ChatListModel model=new ChatListModel();
        presenter=new ChatListPresenter(view,model);
        setPresenter(presenter);
        return myView;
    }
}
