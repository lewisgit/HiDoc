package com.happydoc.lewis.myapplication.Bean;

/**
 * Created by LewisMS on 7/25/2016.
 */
public class PayInfo {
    public static String ONCE_SERVICE="1";
    public static String WEEK_SERVICE="2";
    //int serviceTyp;
    String price,serviceType,details;

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
