package com.happydoc.lewis.myapplication.event;

import java.util.HashMap;

/**
 * Created by Lewis on 2016/7/14.
 */
public class Event<E,L> {
    public HashMap<E,EventListener<L>> eventListener=new HashMap<>();
    public void addEventListener(E event,EventListener<L> listener){
        eventListener.put(event,listener);
    }
    public void dispatch(E e,L data){
        eventListener.get(e).onDispatch(data);
    }
}
