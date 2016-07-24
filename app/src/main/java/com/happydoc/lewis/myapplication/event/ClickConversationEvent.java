package com.happydoc.lewis.myapplication.event;

/**
 * Created by Lewis on 2016/7/24.
 */
public class ClickConversationEvent {
    String convId;
    public ClickConversationEvent(String id){
        this.convId=id;
    }

    public String getConvId() {
        return convId;
    }
}
