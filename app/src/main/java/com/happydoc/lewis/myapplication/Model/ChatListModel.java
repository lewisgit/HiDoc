package com.happydoc.lewis.myapplication.Model;

import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.event.ConversationItemClickEvent;
import com.avoscloud.leanchatlib.utils.AVIMConversationCacheUtils;
import com.happydoc.lewis.myapplication.event.MyCallBack;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

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
    public void getConvRemainTime(String conId,final MyCallBack<Exception> callBack){
        Map<String, Object> dicParameters = new HashMap<>();
        dicParameters.put("convid",conId );
        // �����ƺ���
        AVCloud.callFunctionInBackground("convid_q", dicParameters, new FunctionCallback<Object>() {
            @Override
            public void done(Object o, AVException e) {
                if (e == null) {
                    BigDecimal remTime_d=(BigDecimal)o;
                    long remTime=remTime_d.longValue();
                    if (remTime > 0) {
                        callBack.done(null);
                       // EventBus.getDefault().post(new ConversationItemClickEvent(room.getConversationId()));
                    }
                    else{
                        callBack.done(new Exception());
                       // Toast.makeText(getContext(),getContext().getString(com.avoscloud.leanchatlib.R.string.conv_end),Toast.LENGTH_SHORT).show();
                    }}
                else
                {
                   e.printStackTrace();
                }
            }
        });
    }
}
