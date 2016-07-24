package com.happydoc.lewis.myapplication.Model;

import android.widget.Toast;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.utils.AVIMConversationCacheUtils;
import com.happydoc.lewis.myapplication.event.MyCallBack;

import java.util.List;

/**
 * Created by Lewis on 2016/7/24.
 */
public class ChatListModel {
    public void getAndCacheRecentConv(final MyCallBack<AVIMException> callBack){
        AVIMConversationCacheUtils.getConversationMap().clear();
        final AVIMConversationQuery conversationQuery = ChatManager.getInstance().getConversationQuery();
        conversationQuery.whereContains("m", AVUser.getCurrentUser().getObjectId());
       // conversationQuery.setQueryPolicy(AVQuery.CachePolicy.NETWORK_ONLY);
        conversationQuery.orderByDescending("updatedAt");
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if(e==null){
                    for(AVIMConversation conversation:list)
                    {
                        ChatManager.getInstance().getRoomsTable().insertRoom(conversation.getConversationId());
                        AVIMConversationCacheUtils.getConversationMap().put(conversation.getConversationId(), conversation);
                    }
                    callBack.done(e);
                }
                else
                {
                    callBack.done(e);
                }
            }
        });
    }
}
