package com.happydoc.lewis.myapplication.Presenter;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.avoscloud.leanchatlib.activity.AVChatActivity;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.event.ConversationItemClickEvent;
import com.avoscloud.leanchatlib.model.Room;
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.utils.ConversationManager;
import com.happydoc.lewis.myapplication.Model.ChatListModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.ChatListView;
import com.happydoc.lewis.myapplication.event.ClickConversationEvent;
import com.happydoc.lewis.myapplication.event.Event;
import com.happydoc.lewis.myapplication.event.MainActivityEventBus;
import com.happydoc.lewis.myapplication.event.MyCallBack;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Lewis on 2016/7/24.
 */
public class ChatListPresenter {
    private ChatListView view;
    private ChatListModel model;
    private ConversationManager conversationManager;
    public ChatListPresenter(ChatListView view,ChatListModel model){
        this.view=view;
        this.model=model;
        initialize();
        EventBus.getDefault().register(this);
    }
    public void initialize(){
        conversationManager=ConversationManager.getInstance();
        view.setView();
        view.setChatListAdaper();
        getChatListData();
        view.getRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initChatList();
            }
        });
    }
    public void getChatListData(){
    model.getAndCacheRecentConv(new MyCallBack<AVIMException>() {
        @Override
        public void done(AVIMException data) {
            if(data==null)
            {
                initChatList();
            }else{view.showMsg(data.getMessage());}
        }
    });
}
    private void initChatList() {
        conversationManager.findAndCacheRooms(new Room.MultiRoomsCallback() {
            @Override
            public void done(List<Room> roomList, AVException exception) {
                if (null == exception) {

                    List<Room> sortedRooms = sortRooms(roomList);
                    updateLastMessage(sortedRooms);
                    view.updateChatList(sortedRooms);
                    //chatListViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void updateLastMessage(final List<Room> roomList) {
        for (final Room room : roomList) {
            AVIMConversation conversation = room.getConversation();
            if (null != conversation) {
                conversation.getLastMessage(new AVIMSingleMessageQueryCallback() {
                    @Override
                    public void done(AVIMMessage avimMessage, AVIMException e) {
                        if (null == e && null != avimMessage) {
                            room.setLastMessage(avimMessage);
                            int index = roomList.indexOf(room);
                            view.getChatListAdapter().notifyItemChanged(index);
                        }
                    }
                });
            }
        }
    }
    private List<Room> sortRooms(final List<Room> roomList) {
        List<Room> sortedList = new ArrayList<Room>();
        if (null != roomList) {
            sortedList.addAll(roomList);
            Collections.sort(sortedList, new Comparator<Room>() {
                @Override
                public int compare(Room lhs, Room rhs) {
                    long value = lhs.getLastModifyTime() - rhs.getLastModifyTime();
                    //System.out.println("TIME"+lhs.getLastModifyTime());
                    //System.out.println(rhs.getLastModifyTime());
                    if (value > 0) {
                        return -1;
                    } else if (value < 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
        }
        return sortedList;
    }
    //@Subscribe
    public void onEvent(ConversationItemClickEvent clickEvent) {
        MainActivityEventBus.getEventBus().post(new ClickConversationEvent(clickEvent.conversationId));
    }
}
