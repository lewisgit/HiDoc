package com.happydoc.lewis.myapplication.Bean;

/**
 * Created by LewisMS on 7/25/2016.
 */
public class ConsultInfo {
    static public int BOTH_UNAVAL=1;
    static public int ONCE_AVAL=2;
    static public int WEEK_AVAL=3;
    static public int INVALID=0;
    int consultState;
    String convId;

    public void setConvId(String convId) {
        this.convId = convId;
    }

    public String getConvId() {
        return convId;
    }

    public void setConsultState(int consultState) {
        this.consultState = consultState;
    }

    public int getConsultState() {
        return consultState;
    }
}
