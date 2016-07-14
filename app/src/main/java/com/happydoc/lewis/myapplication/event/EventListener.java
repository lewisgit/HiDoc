package com.happydoc.lewis.myapplication.event;

/**
 * Created by Lewis on 2016/7/14.
 */
public interface EventListener<L> {
    public void onDispatch(L data);
}
