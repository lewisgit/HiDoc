package com.happydoc.lewis.myapplication.event;

/**
 * Created by LewisMS on 7/25/2016.
 */
public class StartConsultEvent {
    public static int ONCE_CONSULT=1;
    public static int WEEK_CONSULT=2;
    int serviceTyp;

    public void setServiceTyp(int serviceTyp) {
        this.serviceTyp = serviceTyp;
    }

    public int getServiceTyp() {
        return serviceTyp;
    }
}
