package com.happydoc.lewis.myapplication.event;

import de.greenrobot.event.EventBus;

/**
 * Created by Lewis on 2016/7/23.
 */
public class MainActivityEventBus{
    static private EventBus eventBus;
    public static void setEventBus(EventBus eventBus) {
        MainActivityEventBus.eventBus = eventBus;
    }
    public static EventBus getEventBus() {
        return eventBus;
    }
}
