package com.happydoc.lewis.myapplication.View;

import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avoscloud.leanchatlib.adapter.CommonListAdapter;
import com.avoscloud.leanchatlib.model.Room;
import com.avoscloud.leanchatlib.view.DividerItemDecoration;
import com.avoscloud.leanchatlib.viewholder.ConversationItemHolder;
import com.happydoc.lewis.myapplication.R;

import java.util.List;

/**
 * Created by Lewis on 2016/7/24.
 */
public class ChatListView extends FragmentView{
    SwipeRefreshLayout refreshLayout;
    RecyclerView chatListView;
    CommonListAdapter<Room> chatListAdapter;
    LinearLayoutManager layoutManager;
    public ChatListView(Fragment fragment, View view){
        super(fragment,view);
    }
    public void setView(){
        chatListView=(RecyclerView) getView(R.id.chat_list);
        refreshLayout=(SwipeRefreshLayout)getView(R.id.chat_list_refresh);
    }
    public void setChatListAdaper(){
        layoutManager = new LinearLayoutManager(fragment.getActivity());
        chatListView.setLayoutManager(layoutManager);
        chatListView.addItemDecoration(new DividerItemDecoration(fragment.getActivity()));
        chatListAdapter=new CommonListAdapter<Room>(ConversationItemHolder.class);
        chatListView.setAdapter(chatListAdapter);
    }
    public void updateChatList(List<Room> roomList){
        chatListAdapter.setDataList(roomList);
        chatListAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public CommonListAdapter<Room> getChatListAdapter() {
        return chatListAdapter;
    }
}
