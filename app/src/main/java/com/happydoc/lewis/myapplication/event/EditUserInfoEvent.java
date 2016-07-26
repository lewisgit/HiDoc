package com.happydoc.lewis.myapplication.event;

/**
 * Created by Lewis on 2016/7/26.
 */
public class EditUserInfoEvent {
    int eventId;

    public int getEventId() {
        return eventId;
    }

    public EditUserInfoEvent(int id){
        this.eventId=id;
    }
}
